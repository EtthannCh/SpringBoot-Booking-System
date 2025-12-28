package com.example.booking_system.auth.role.model;

import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.example.booking_system.header.HeaderCollections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleCrudDto {
    private String name;
    private RoleCodeEnum code;
    private String description;

    public Role toRecord(RoleCrudDto roleCrudDto, HeaderCollections header) {
        if (roleCrudDto == null)
            return null;

        return new Role(null, name, code, description, header.getUserId(), header.getUserName(), null, null, null,
                null);
    }
}
