package com.example.booking_system.booking.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public record BookingDetail(
    @Id Long id,
    Long booking_id,
    Double price,
    Long[] seat_id,
    UUID created_by_id,
    String created_by,
    LocalDateTime created_at
) {
    
}
