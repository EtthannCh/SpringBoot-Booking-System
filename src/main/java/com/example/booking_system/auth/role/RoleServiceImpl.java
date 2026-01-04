package com.example.booking_system.auth.role;

import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.booking_system.auth.role.model.RoleCrudDto;
import com.example.booking_system.auth.role.model.RoleDto;
import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.example.booking_system.auth.user_account.UserAccountRepository;
import com.example.booking_system.auth.user_account.model.UserAccountDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.utils.CheckUtil;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private UserAccountRepository userAccountRepository;

    private Map<String, String> duplicateKeyMap = Map.of(
            "role_name_key", "AUTH_ROLE_NAMEEXIST",
            "role_code_key", "AUTH_ROLE_CODEEXIST");

    public RoleServiceImpl(
            RoleRepository roleRepository,
            UserAccountRepository userAccountRepository) {
        this.roleRepository = roleRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Long createRole(RoleCrudDto roleCrudDto, HeaderCollections header) throws Exception {
        try {
            UserAccountDto userLogin = userAccountRepository.findByUserId(header.getUserId())
                    .orElseThrow(() -> new BusinessException("AUTH_ROLE_USERNOTFOUND"));

            if (userLogin.getRoleCode().equals(RoleCodeEnum.USER))
                throw new BusinessException("AUTH_ROLE_UNAUTHORIZEDUSER");

            return roleRepository.createRole(roleCrudDto.toRecord(roleCrudDto, header));
        } catch (DuplicateKeyException e) {
            CheckUtil.throwUniqueException(e, duplicateKeyMap);
            throw e;
        }
    }

}
