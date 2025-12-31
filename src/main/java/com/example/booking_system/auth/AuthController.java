package com.example.booking_system.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_system.auth.user_account.UserAccountService;
import com.example.booking_system.auth.user_account.model.UserAccountCrudDto;
import com.example.booking_system.security.JwtUtil;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final UserAccountService userAccountService;

    public AuthController(
            JwtUtil jwtUtil,
            UserAccountService userAccountService
        ) {
        this.jwtUtil = jwtUtil;
        this.userAccountService = userAccountService;
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody String username) {
        String verificationToken = jwtUtil.generateToken(username);
        return verificationToken;
    }

    @PostMapping("/refresh-token")
    public String refreshToken(@RequestBody UserAccountCrudDto userAccountCrudDto) throws Exception{
        return userAccountService.refreshToken(userAccountCrudDto);
    }
}
