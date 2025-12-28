package com.example.booking_system.auth.user_account.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public record UserAccount(
        @Id Long id,
        UUID user_id,
        String email,
        String password,
        String full_name,
        Long role_id,
        LocalDateTime created__at,
        String created_by,
        UUID created_by_id,
        LocalDateTime last_updated__at,
        String last_updated_by,
        UUID last_updated_by_id) {

}
