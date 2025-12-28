package com.example.booking_system.auth.role;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_system.auth.role.model.RoleCrudDto;
import com.example.booking_system.constant.HeaderConstants;
import com.example.booking_system.header.HeaderCollections;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("role")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create Role")
    })
    public Long createRole(
            @RequestBody RoleCrudDto roleCrudDto,
            @RequestHeader(HeaderConstants.USER_ID) UUID userId,
            @RequestHeader(HeaderConstants.USER_NAME) String userName) throws Exception {
        HeaderCollections header = new HeaderCollections().setUserId(userId).setUserName(userName);
        return roleService.createRole(roleCrudDto, header);
    }
}
