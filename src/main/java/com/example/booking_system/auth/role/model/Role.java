package com.example.booking_system.auth.role.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;

public record Role(
        @Id Long id,
        String name,
        RoleCodeEnum code,
        String description,
        UUID created_by_id,
        String created_by,
        LocalDateTime created_at,
        UUID last_updated_by_id,
        String last_updated_by,
        LocalDateTime last_updated_at) {
}
