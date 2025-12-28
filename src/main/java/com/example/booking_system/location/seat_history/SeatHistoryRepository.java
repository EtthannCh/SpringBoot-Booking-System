package com.example.booking_system.location.seat_history;

import java.sql.Types;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.booking_system.location.seat_history.model.seat_history.SeatHistory;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryDto;

@Repository
public class SeatHistoryRepository {

    private final JdbcClient jdbcClient;

    public SeatHistoryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<SeatHistoryDto> findSeatHistoryById(Long id) {
        return jdbcClient.sql("""
                select *
                from seat_history
                where id = :id
                order by id desc limit 1
                """)
                .param("id", id)
                .query(SeatHistoryDto.class)
                .optional();
    }

    public Long processReserveSeat(SeatHistory seatHistory) {
        return jdbcClient.sql("""
                insert into seat_history
                (
                    code, status, location_id,
                    created_by_id, created_by, created_at,
                    last_updated_by_id, last_updated_by, last_updated_at
                )
                values
                (
                    :code, :status, :locationId,
                    :createdById, :createdBy, now(),
                    :lastUpdatedById, :lastUpdatedBy, now()
                )
                returning id;
                """)
                .param("code", seatHistory.code())
                .param("status", seatHistory.status(), Types.VARCHAR)
                .param("locationId", seatHistory.location_id())
                .param("createdById", seatHistory.created_by_id())
                .param("createdBy", seatHistory.created_by())
                .param("lastUpdatedById", seatHistory.last_updated_by_id())
                .param("lastUpdatedBy", seatHistory.last_updated_by())
                .query(Long.class).single();
    }
}
