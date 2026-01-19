package com.example.booking_system.booking;

import java.sql.Types;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.booking_system.booking.model.Booking;
import com.example.booking_system.booking.model.BookingDto;
import com.example.booking_system.booking.model.BookingEnum.BookingStatus;
import com.example.booking_system.header.HeaderCollections;

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
                .param("status", BookingStatus.RESERVED, Types.VARCHAR)
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

    public Double findBookingCountForSingleEventPerPeriod(Long eventId, LocalTime showTime) {
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

    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Optional<BookingDto> findBookingById(Long id) {
        return jdbcCLient.sql("""
                select *
                from booking
                where id = :id
                """)
                .param("id", id)
                .query(BookingDto.class)
                .optional();
    }

    public List<BookingDto> findExpiredBookings() {
        return jdbcCLient.sql("""
                select *
                    from booking
                    where now() - created_at > interval '10 minutes'
                    and status = 'RESERVED'
                """)
                .query(BookingDto.class)
                .list();
    }

    public void cancelBookingByScheduler(Long bookingId) {
        jdbcCLient.sql("""
                    update booking
                    set
                        status = 'CANCELLED',
                        last_updated_by = 'SCHEDULER',
                        last_updated_by_id = '542d0ce8-ea21-4cb3-9f2b-0103b502507d',
                        last_updated_at = now()
                    where id = :id
                """)
                .param("id", bookingId)
                .update();
    }

    public void cancelBooking(Long bookingId, HeaderCollections header) {
        jdbcCLient.sql("""
                    update booking
                    set
                        status = 'CANCELLED',
                        last_updated_by = :lastUpdatedBy,
                        last_updated_by_id = :lastUpdatedById,
                        last_updated_at = now()
                    where id = :id
                """)
                .param("id", bookingId)
                .param("lastUpdatedBy", header.getUserName())
                .param("lastUpdatedById", header.getUserId())
                .update();
    }

    public void confirmBooking(Long bookingId, HeaderCollections header) {
        jdbcCLient.sql("""
                    update booking
                    set
                        status = 'COMPLETED',
                        last_updated_by = :lastUpdatedBy,
                        last_updated_by_id = :lastUpdatedById,
                        last_updated_at = now()
                    where id = :id
                """)
                .param("id", bookingId)
                .param("lastUpdatedBy", header.getUserName())
                .param("lastUpdatedById", header.getUserId())
                .update();
    }
}
