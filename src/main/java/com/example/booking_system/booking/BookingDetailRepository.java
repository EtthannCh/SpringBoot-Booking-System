package com.example.booking_system.booking;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.booking.model.BookingDetail;
import com.example.booking_system.booking.model.BookingDetailDto;

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

    public List<BookingDetailDto> findListByBookingId(Long bookingId) {
        List<BookingDetail> bookingDetailList = jdbcClient.sql("""
                select
                    id,
                    booking_id,
                    price,
                    seat_id::int4[],
                    created_at,
                    created_by,
                    created_by_id
                from
                    booking_detail
                where booking_id = :id
                """)
                .param("id", bookingId)
                .query(BookingDetail.class)
                .list();

        return BookingDetailDto.fromRecordList(bookingDetailList);
    }
}
