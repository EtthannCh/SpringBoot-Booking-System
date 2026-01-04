package com.example.booking_system.auth.user_account;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.booking_system.auth.role.RoleService;
import com.example.booking_system.auth.role.model.RoleDto;
import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.example.booking_system.auth.user_account.model.UserAccountCrudDto;
import com.example.booking_system.auth.user_account.model.UserAccountDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.security.JwtUtil;
import com.example.booking_system.utils.CheckUtil;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder encoder;

    @Autowired
    private final JwtUtil jwtUtil;

    private Map<String, String> duplicateKeyMap = new HashMap<>(
            Map.ofEntries(Map.entry("user_name_key", "BOK_USER_EMAILEXIST")));

    public UserAccountServiceImpl(
            UserAccountRepository userAccountRepository,
            RoleService roleService,
            AuthenticationManager authenticationManager,
            PasswordEncoder encoder,
            JwtUtil jwtUtil) {
        this.userAccountRepository = userAccountRepository;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public UUID createUserAccount(UserAccountCrudDto userAccountCrudDto, HeaderCollections header)
            throws Exception {
        try {
            Optional<UserAccountDto> user = userAccountRepository
                    .findByUsernameAndEmail(userAccountCrudDto.getFullName(), userAccountCrudDto.getEmail());
            if (user.isPresent())
                throw new BusinessException("AUTH_USERACCOUNT_USEREXIST");

            checkTokenExpired(userAccountCrudDto.getToken());
            jwtUtil.validateJwtToken(userAccountCrudDto.getToken());

            DecodedJWT decodedNewJWT = JWT.decode(userAccountCrudDto.getToken());

            Date now = new Date();
            LocalDateTime nowTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());
            // if previous token exist
            if (userAccountCrudDto.getPreviousToken() != null && !userAccountCrudDto.getPreviousToken().equals("")) {
                DecodedJWT decodedPreviousJWT = JWT.decode(userAccountCrudDto.getPreviousToken());
                Date previousExpiredAt = decodedPreviousJWT.getExpiresAt();
                LocalDateTime previousExpiredDateTime = LocalDateTime.ofInstant(previousExpiredAt.toInstant(),
                        ZoneId.systemDefault());

                if (nowTime.isBefore(previousExpiredDateTime))
                    throw new BusinessException("AUTH_USERACCOUNT_PREVIOUSTOKENNOTEXPIRED");
            }
            Date expiredAt = decodedNewJWT.getExpiresAt();

            LocalDateTime expiredDateTime = LocalDateTime.ofInstant(expiredAt.toInstant(), ZoneId.systemDefault());

            if (nowTime.isAfter(expiredDateTime))
                throw new BusinessException("AUTH_USERACCOUNT_TOKENEXPIRED");

            String pwHashed = BCrypt.hashpw(userAccountCrudDto.getPassword(), BCrypt.gensalt());
            userAccountCrudDto.setPassword(pwHashed);
            return userAccountRepository.createAccount(userAccountCrudDto.toRecord(header));
        } catch (DuplicateKeyException e) {
            CheckUtil.throwUniqueException(e, duplicateKeyMap);
            throw e;
        }
    }

    @Override
    public void updateUserAccount(UserAccountCrudDto userAccountCrudDto, HeaderCollections header)
            throws Exception {
        String hashedPassword = BCrypt.hashpw(userAccountCrudDto.getPassword(), BCrypt.gensalt());
        userAccountCrudDto.setPassword(hashedPassword);
        checkCredentials(userAccountCrudDto, header);

        userAccountRepository.updateAccountPassword(userAccountCrudDto.toRecord(header));
    }

    public void checkCredentials(UserAccountCrudDto userAccountCrudDto, HeaderCollections header) {
        UserAccountDto userLogin = userAccountRepository.findByUserId(header.getUserId())
                .orElseThrow(() -> new BusinessException("AUTH_USERACCOUNT_USERNOTFOUND"));
        UserAccountDto user = userAccountRepository.findByUserId(userAccountCrudDto.getUserId())
                .orElseThrow(() -> new BusinessException("AUTH_USERACCOUNT_USERNOTFOUND"));

        RoleDto userLoginRole = roleService.findById(userLogin.getRoleId())
                .orElseThrow(() -> new BusinessException("AUTH_USERACCOUNT_ROLENOTFOUND"));
        RoleDto userRole = roleService.findById(user.getRoleId())
                .orElseThrow(() -> new BusinessException("AUTH_USERACCOUNT_ROLENOTFOUND"));

        if ((userLoginRole.getCode().equals(RoleCodeEnum.USER)
                && EnumSet.of(RoleCodeEnum.ADM, RoleCodeEnum.SYS_ADM).contains(userRole.getCode()))
                ||
                (userLoginRole.getCode().equals(RoleCodeEnum.ADM)
                        && EnumSet.of(RoleCodeEnum.ADM).contains(userRole.getCode())))
            throw new BusinessException("AUTH_USERACCOUNT_INVALIDACCESS");
    }

    @Override
    public Optional<UserAccountDto> findByUserId(UUID userId) {
        return userAccountRepository.findByUserId(userId);
    }

    @Override
    public void login(UserAccountCrudDto userAccountCrudDto, HeaderCollections header) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAccountCrudDto.getFullName(),
                    userAccountCrudDto.getPassword()));

            checkTokenExpired(userAccountCrudDto.getToken());

            jwtUtil.validateJwtToken(userAccountCrudDto.getToken());

            UserAccountDto userLogin = userAccountRepository.findByUserId(header.getUserId())
                    .orElseThrow(() -> new BusinessException("AUTH_USERACCOUNT_USERNOTFOUND"));
            if (!BCrypt.checkpw(userAccountCrudDto.getPassword(), userLogin.getPassword()))
                throw new BusinessException("AUTH_USERACCOUNT_PASSWORDINVALID");

        } catch (Exception e) {
            throw e;
        }
    }

    private void checkTokenExpired(String token) throws Exception {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiredAt = decodedJWT.getExpiresAt();
        Date now = new Date();

        LocalDateTime expiredDateTime = LocalDateTime.ofInstant(expiredAt.toInstant(), ZoneId.systemDefault());
        LocalDateTime nowTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());

        if (nowTime.isAfter(expiredDateTime))
            throw new BusinessException("AUTH_USERACCOUNT_TOKENEXPIRED");
    }

    @Override
    public String refreshToken(UserAccountCrudDto userAccountCrudDto) throws Exception {
        UserAccountDto user = userAccountRepository.findByUsername(userAccountCrudDto.getFullName())
                .orElseThrow(() -> new BusinessException("AUTH_USERACCOUNT_USERNOTFOUND"));
        if (!BCrypt.checkpw(userAccountCrudDto.getPassword(), user.getPassword()))
            throw new BusinessException("AUTH_USERACCOUNT_PASSWORDINVALID");

        DecodedJWT decodedJWT = JWT.decode(userAccountCrudDto.getPreviousToken());
        Date expiredAt = decodedJWT.getExpiresAt();
        Date now = new Date();

        LocalDateTime expiredDateTime = LocalDateTime.ofInstant(expiredAt.toInstant(), ZoneId.systemDefault());
        LocalDateTime nowTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());
        if (expiredDateTime.isBefore(nowTime))
            throw new BusinessException("AUTH_USERACCOUNT_TOKENNOTEXPIRED");

        return jwtUtil.generateToken(userAccountCrudDto.getFullName());
    }
}
