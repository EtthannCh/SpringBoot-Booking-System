package com.example.booking_system.auth.user_account.model;

import java.util.UUID;

import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.example.booking_system.header.HeaderCollections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountCrudDto {
    private UUID userId;
    private String email;
    private String password;
    private String fullName;
    private Long roleId;
    private RoleCodeEnum roleCode;

    // JWT
    private String token;
    private String previousToken;

    public UserAccount toRecord(HeaderCollections header) {
        return new UserAccount(null, header.getUserId(), email, password, fullName, roleId, null,
                fullName,
                header.getUserId(), null, fullName, header.getUserId());
    }
}
