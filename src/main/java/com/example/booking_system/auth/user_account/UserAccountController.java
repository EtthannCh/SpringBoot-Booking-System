package com.example.booking_system.auth.user_account;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_system.auth.user_account.model.UserAccountCrudDto;
import com.example.booking_system.constant.HeaderConstants;
import com.example.booking_system.header.HeaderCollections;

@RestController
@RequestMapping("/account")
public class UserAccountController {
    private UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/register")
    public UUID registerUser(
            @RequestBody UserAccountCrudDto userAccountCrudDto,
            @RequestHeader(HeaderConstants.USER_ID) UUID userId,
            @RequestHeader(HeaderConstants.USER_NAME) String userName) throws Exception {
        HeaderCollections header = new HeaderCollections()
                .setUserId(userId).setUserName(userName);
        return userAccountService.createUserAccount(userAccountCrudDto, header);
    }

    @PostMapping("/login")
    public void loginUser(
            @RequestBody UserAccountCrudDto userAccountCrudDto,
            @RequestHeader(HeaderConstants.USER_ID) UUID userId,
            @RequestHeader(HeaderConstants.USER_NAME) String userName) throws Exception {
        HeaderCollections header = new HeaderCollections().setUserId(userId).setUserName(userName);
        userAccountService.login(userAccountCrudDto, header);
    }

}
