package com.example.booking_system.charge_item;

import java.sql.Types;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.charge_item.model.ChargeItem;
import com.example.booking_system.charge_item.model.ChargeItemDto;
import com.example.booking_system.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ChargeItemRepository {

    private final JdbcClient jdbcClient;

    public ChargeItemRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long create(ChargeItem ci) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                insert into charge_item
                (
                    booking_id, status ,qty, unit_price, total_price,
                    created_by_id, created_by, created_at,
                    last_updated_by, last_updated_by_id, last_updated_at
                )
                values
                (
                    :bookingId, :status ,:qty, :unitPrice, :totalPrice,
                    :createdById, :createdBy, now(),
                    :lastUpdatedBy, :lastUpdatedById, now()
                )
                    """)
                .param("bookingId", ci.booking_id())
                .param("status", ci.charge_item_status(), Types.VARCHAR)
                .param("qty", ci.qty())
                .param("unitPrice", ci.unit_price())
                .param("totalPrice", ci.total_price())
                .param("createdBy", ci.created_by())
                .param("createdById", ci.created_by_id())
                .param("lastUpdatedBy", ci.last_updated_by())
                .param("lastUpdatedById", ci.last_updated_by_id())
                .update(keyHolder, "id");

        return keyHolder.getKey().longValue();
    }

    public Optional<ChargeItemDto> findByBookingId(Long bookingId) {
        return jdbcClient.sql("""
                    select *
                    from charge_item
                    where booking_id = :bookingId
                """)
                .param("bookingId", bookingId)
                .query(ChargeItemDto.class)
                .optional();
    }

    public void cancelChargeItemByBookingId(ChargeItem ci) throws Exception {
        int update = jdbcClient.sql("""
                update charge_item
                set
                status = 'ABORTED',
                last_updated_by = :lastUpdatedBy,
                last_updated_by_id = :lastUpdatedById,
                last_updated_at = now()
                where booking_id = :bookingId
                """)
                .param("lastUpdatedBy", ci.last_updated_by())
                .param("lastUpdatedById", ci.last_updated_by_id())
                .param("bookingId", ci.booking_id())
                .update();
        if (update == 0)
            throw new BusinessException("BOK_CHARGEITEM_IDNOTFOUND");
    }
}
