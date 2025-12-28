package com.example.booking_system.auth.role.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {
    private Long id;
    private String name;
    private RoleCodeEnum code;
    private String description;
    private UUID createdById;
    private String createdBy;
    private LocalDateTime createdAt;
    private UUID lastUpdatedById;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;

    public RoleDto fromRecord(Role role) {
        if (role == null)
            return null;

        return new RoleDto()
                .setId(role.id())
                .setName(role.name())
                .setCode(role.code())
                .setDescription(role.description())
                .setCreatedAt(role.created_at())
                .setCreatedBy(role.created_by())
                .setCreatedById(role.created_by_id())
                .setLastUpdatedAt(role.last_updated_at())
                .setLastUpdatedBy(role.last_updated_by())
                .setLastUpdatedById(role.last_updated_by_id());
    }
}
