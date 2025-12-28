package com.example.booking_system.event.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public record Event(
        @Id Long id,
        String name,
        Long location_id,
        String location_name,
        String description,
        LocalDateTime period_start,
        LocalDateTime period_end,
        UUID created_by_id,
        String created_by,
        LocalDateTime created_at,
        UUID last_updated_by_id,
        String last_updated_by,
        LocalDateTime last_updated_at,
        String[] start_time) {

}
