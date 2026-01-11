package com.example.booking_system.auth.user_account;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.booking_system.auth.user_account.model.UserAccountCrudDto;
import com.example.booking_system.auth.user_account.model.UserAccountDto;
import com.example.booking_system.header.HeaderCollections;

@Service
public interface UserAccountService {
    public UUID createUserAccount(UserAccountCrudDto userAccountCrudDto, HeaderCollections header) throws Exception;

    public void updateUserAccount(UserAccountCrudDto userAccountCrudDto, HeaderCollections header) throws Exception;

    public Optional<UserAccountDto> findByUserId(UUID userId);

    public Optional<UserAccountDto> findByUsername(String username, String email);

    public String login(UserAccountCrudDto userAccountCrudDto, HeaderCollections header) throws Exception;

    public String refreshToken(UserAccountCrudDto userAccountCrudDto) throws Exception;
}
