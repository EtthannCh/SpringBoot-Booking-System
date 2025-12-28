package com.example.booking_system.booking;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.booking.model.BookingDetail;

@Repository
public class BookingDetailRepository {

    private final JdbcClient jdbcClient;

    public BookingDetailRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long create(BookingDetail bookingDetail) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                insert into booking_detail
                (
                    booking_id, price, seat_id,
                    created_at, created_by, created_by_id
                )
                values
                (
                    :bookingId, :price, :seatId,
                    :createdAt, :createdBy, now()
                )
                """)
                .param("bookingId", bookingDetail.booking_id())
                .param("price", bookingDetail.price())
                .param("seatId", bookingDetail.seat_id())
                .param("createdAt", bookingDetail.created_at())
                .param("createdBy", bookingDetail.created_by())
                .update(keyHolder, "keyHolder");
        var id = keyHolder.getKey();
        return id.longValue();
    }
}
