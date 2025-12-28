package com.example.booking_system.auth.user_account;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.swing.plaf.metal.MetalTheme;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.booking_system.auth.role.RoleService;
import com.example.booking_system.auth.role.model.RoleDto;
import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.example.booking_system.auth.user_account.model.UserAccountCrudDto;
import com.example.booking_system.auth.user_account.model.UserAccountDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.utils.CheckUtil;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;

    private Map<String, String> duplicateKeyMap = new HashMap<>(
            Map.ofEntries(Map.entry("user_name_key", "BOK_USER_EMAILEXIST")));

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, RoleService roleService) {
        this.userAccountRepository = userAccountRepository;
        this.roleService = roleService;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public UUID createUserAccount(UserAccountCrudDto userAccountCrudDto, HeaderCollections header)
            throws Exception {
        try {
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

    public boolean login(UserAccountCrudDto userAccountCrudDto, HeaderCollections header) throws Exception {
        UserAccountDto userLogin = userAccountRepository.findByUserId(header.getUserId())
                .orElseThrow(() -> new BusinessException("AUTH_USERACCOUNT_USERNOTFOUND"));
        if (!BCrypt.checkpw(userAccountCrudDto.getPassword(), userLogin.getPassword()))
            return false;

        return true;
    }
}
