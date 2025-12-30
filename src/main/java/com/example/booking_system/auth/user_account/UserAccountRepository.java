package com.example.booking_system.auth.user_account;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.auth.user_account.model.UserAccount;
import com.example.booking_system.auth.user_account.model.UserAccountDto;
import com.example.booking_system.exception.BusinessException;

@Repository
public class UserAccountRepository {
    private final JdbcClient jdbcClient;

    public UserAccountRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public UUID createAccount(UserAccount userAccount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                insert into users
                (
                    email, password_hash,
                    name, role_id,
                    created_at
                )
                values
                (
                    :email, :password,
                    :name, :roleId,
                    now()
                )
                returning user_id;
                    """)
                .param("email", userAccount.email())
                .param("password", userAccount.password())
                .param("name", userAccount.name())
                .param("roleId", userAccount.role_id())
                .update(keyHolder, "keyholder");

        var id = keyHolder.getKeys().get("user_id");
        return (UUID) id;
    }

    public void updateAccountPassword(UserAccount userAccount) throws Exception {
        int update = jdbcClient.sql("""
                update users
                set
                    password_hash = :pw,
                    last_updated_by_id = :lastUpdatedBy,
                    last_updated_by = :lastUpdatedBy,
                    last_updated_at = now()
                where user_id = :id
                    """)
                .param("id", userAccount.user_id())
                .param("email", userAccount.email())
                .param("passwordHash", userAccount.password())
                .param("lastUpdatedBy", userAccount.last_updated_by())
                .param("lastUpdatedById", userAccount.last_updated_by_id())
                .update();

        if (update == 0)
            throw new BusinessException("AUTH_USERACCOUNT_IDNOTFOUND");

    }

    public Optional<UserAccountDto> findByUserId(UUID userId) {
        return jdbcClient.sql("""
                    select u.*, r.code roleCode, u.password_hash password
                    from users u
                    inner join roles r on u.role_id = r.id
                    where user_id = :userId
                """)
                .param("userId", userId)
                .query(UserAccountDto.class)
                .optional();
    }

    public Optional<UserAccountDto> findByUsername(String username) {
        return jdbcClient.sql("""
                    select 
                        u.id userAccountId,
                        u.user_id userId,
                        email,
                        r.code roleCode, u.password_hash password,
                        u.name name,
                        u.role_id roleId
                    from users u
                    inner join roles r on u.role_id = r.id
                    where upper(u.name) = upper(:username)
                """)
                .param("username", username)
                .query(UserAccountDto.class)
                .optional();
    }
}
