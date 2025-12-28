package com.example.booking_system.auth.role;

import java.sql.Types;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.auth.role.model.Role;
import com.example.booking_system.auth.role.model.RoleDto;

@Repository
public class RoleRepository {

    private final JdbcClient jdbcClient;

    public RoleRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<RoleDto> findById(Long id) {
        return jdbcClient.sql("""
                select *
                from roles
                where id = :id
                """)
                .param("id", id)
                .query(RoleDto.class)
                .optional();
    }

    public Long createRole(Role role) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                    insert into roles
                    (
                        name, code, description,
                        created_by, created_by_id, created_at,
                        last_updated_by, last_updated_by_id, last_updated_at
                    )
                    values
                    (
                        :name, :code, :description,
                        :createdBy, :createdById, now(),
                        :createdBy, :createdById, now()
                    )
                """)
                .param("name", role.name().toUpperCase())
                .param("code", role.code(), Types.VARCHAR)
                .param("description", role.description())
                .param("createdBy", role.created_by())
                .param("createdById", role.created_by_id())
                .update(keyHolder, "id");
        return keyHolder.getKey().longValue();
    }
}
