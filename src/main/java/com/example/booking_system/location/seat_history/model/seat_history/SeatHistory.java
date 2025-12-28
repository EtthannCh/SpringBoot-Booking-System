package com.example.booking_system.location.seat_history.model.seat_history;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryEnum.SeatHistoryStatus;

public record SeatHistory(
        @Id Long id,
        String code,
        SeatHistoryStatus status,
        Long location_id,
        LocalDateTime created__at,
        String created_by,
        UUID created_by_id,
        LocalDateTime last_updated__at,
        String last_updated_by,
        UUID last_updated_by_id) {

}
