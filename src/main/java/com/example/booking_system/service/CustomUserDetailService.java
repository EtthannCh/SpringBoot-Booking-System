package com.example.booking_system.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.booking_system.auth.user_account.UserAccountRepository;
import com.example.booking_system.auth.user_account.model.UserAccountDto;
import com.example.booking_system.exception.BusinessException;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountDto user = userAccountRepository.findByUsername(username).orElseThrow(() -> new BusinessException("AUTH_USER_USERNAMENOTFOUND"));
        return new User(user.getFullName(), user.getPassword(), Collections.emptyList());
    }
    
}
