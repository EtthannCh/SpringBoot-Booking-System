package com.example.booking_system.booking.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.example.booking_system.booking.model.BookingEnum.BookingStatus;

public record Booking(
        @Id Long id,
        UUID user_id,
        Long event_id,
        BookingStatus status,
        String booking_no,
        LocalTime show_time,
        Double qty,
        LocalDateTime created_at,
        String created_by,
        UUID created_by_id,
        LocalDateTime last_updated_at,
        String last_updated_by,
        UUID last_updated_by_id) {

}
