package com.example.booking_system.auth;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.booking_system.auth.user_account.UserAccountRepository;
import com.example.booking_system.auth.user_account.model.UserAccountCrudDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.security.JwtUtil;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserAccountRepository userAccountRepository;

    @Autowired
    private final PasswordEncoder encoder;

    @Autowired
    private final JwtUtil jwtUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserAccountRepository userAccountRepository,
            PasswordEncoder encoder,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userAccountRepository = userAccountRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserAccountCrudDto userAccountCrudDto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAccountCrudDto.getFullName(),
                    userAccountCrudDto.getPassword()));

            DecodedJWT decodedJWT = JWT.decode(userAccountCrudDto.getToken());
            Date expiredAt = decodedJWT.getExpiresAt();
            Date now = new Date();

            LocalTime expiredTime = LocalDateTime.ofInstant(expiredAt.toInstant(), ZoneId.systemDefault())
                    .toLocalTime();
            LocalTime nowTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).toLocalTime();

            if (nowTime.isAfter(expiredTime))
                throw new BusinessException("BOK_AUTH_TOKENEXPIRED");

            jwtUtil.validateJwtToken(userAccountCrudDto.getToken());
            return "Successfully Login";
        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserAccountCrudDto userAccountCrudDto) {
        String verificationToken = jwtUtil.generateToken(userAccountCrudDto.getFullName());
        return verificationToken;
    }

}
