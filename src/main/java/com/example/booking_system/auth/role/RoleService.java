package com.example.booking_system.auth.role;

import java.util.Optional;

import com.example.booking_system.auth.role.model.RoleCrudDto;
import com.example.booking_system.auth.role.model.RoleDto;
import com.example.booking_system.header.HeaderCollections;

public interface RoleService {
    public Optional<RoleDto> findById(Long id);

    public Long createRole(RoleCrudDto roleCrudDto, HeaderCollections header) throws Exception;
}
