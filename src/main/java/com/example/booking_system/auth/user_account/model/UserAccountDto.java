package com.example.booking_system.auth.user_account.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountDto {
    private Long userAccountId;
    private UUID userId;
    private String email;
    private String password;
    private String fullName;
    private Long roleId;

    // role
    private RoleCodeEnum roleCode;

    public UserAccountDto fromRecord(UserAccount ua) {
        if (ua == null)
            return null;

        return new UserAccountDto()
                .setUserAccountId(ua.id())
                .setUserId(ua.user_id())
                .setEmail(ua.email())
                .setPassword(ua.password())
                .setFullName(ua.full_name())
                .setRoleId(ua.role_id());
    }

    public List<UserAccountDto> fromRecordList(List<UserAccount> uaList) {
        if (uaList.isEmpty())
            return Collections.emptyList();

        List<UserAccountDto> uaDtoList = new ArrayList<>();
        for (UserAccount userAccount : uaList) {
            uaDtoList.add(fromRecord(userAccount));
        }
        return uaDtoList;
    }
}
