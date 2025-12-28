package com.example.booking_system.booking;

import java.sql.Types;
import java.time.LocalTime;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.booking.model.Booking;
import com.example.booking_system.booking.model.BookingEnum.BookingStatus;

@Repository
public class BookingRepository {
    private final JdbcClient jdbcCLient;

    public BookingRepository(JdbcClient jdbcClient) {
        this.jdbcCLient = jdbcClient;
    }

    public Long create(Booking booking) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcCLient.sql("""
                insert into booking
                (
                    user_id, event_id, status, booking_no, qty, show_time,
                    created_at, created_by, created_by_id
                )
                values
                (
                    :userId, :eventId, :status, :bookingNo, :qty, :showTime,
                    now(), :createdBy, :createdById
                )
                """)
                .param("userId", booking.user_id())
                .param("eventId", booking.event_id())
                .param("status", BookingStatus.DRAFT, Types.VARCHAR)
                .param("bookingNo", booking.booking_no())
                .param("qty", booking.qty())
                .param("showTime", booking.show_time())
                .param("createdBy", booking.created_by())
                .param("createdById", booking.created_by_id())
                .param("lastUpdatedBy", booking.last_updated_by())
                .param("lastUpdatedById", booking.last_updated_by_id())
                .update(keyHolder, "id");
        var id = keyHolder.getKey();
        return id.longValue();
    }

    public Double findBookingCountForSingleEventPerPeriod(Long eventId, LocalTime showTime){
        return jdbcCLient.sql("""
                select coalesce(count(*),0)
                from booking b
                where b.event_id = :eventId
                and b.show_time::time = :showTime
                and b.created_at::date >= now() and b.created_at::date <= now()
                """)
                .param("eventId", eventId)
                .param("showTime", showTime)
                .query(Double.class)
                .single();
    }
}
