package com.example.booking_system.booking;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.booking.model.BookingDetail;
import com.example.booking_system.booking.model.BookingDetailDto;
import com.example.booking_system.exception.BusinessException;

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
                    :bookingId, :price, :seatId::int[],
                    now(), :createdBy, :createdById
                )
                """)
                .param("bookingId", bookingDetail.booking_id())
                .param("price", bookingDetail.price())
                .param("seatId", bookingDetail.seat_id())
                .param("createdById", bookingDetail.created_by_id())
                .param("createdBy", bookingDetail.created_by())
                .update(keyHolder, "id");
        var id = keyHolder.getKey();
        return id.longValue();
    }

    public Optional<BookingDetailDto> findListByBookingId(Long bookingId) {
        Optional<BookingDetail> bookingDetailList = jdbcClient.sql("""
                select
                    id,
                    booking_id,
                    price,
                    array_to_string(seat_id, ',') seat_id,
                    created_at,
                    created_by,
                    created_by_id
                from
                    booking_detail
                where booking_id = :id
                """)
                .param("id", bookingId)
                .query(BookingDetail.class)
                .optional();
        if (!bookingDetailList.isPresent())
            throw new BusinessException("BOK_BOOKINGDETAIL_IDNOTFOUND");

        return Optional.of(BookingDetailDto.fromRecord(bookingDetailList.get()));
    }
}
