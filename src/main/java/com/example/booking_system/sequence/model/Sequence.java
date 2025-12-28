package com.example.booking_system.sequence.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.example.booking_system.sequence.model.SequenceEnum.SequenceResetCondition;

public record Sequence(
        @Id Long id,
        String name,
        String format,
        Integer current_number,
        Integer start_no,
        SequenceResetCondition reset_condition,
        UUID created_by_id,
        String created_by,
        LocalDateTime created_at,
        UUID last_updated_by_id,
        String last_updated_by,
        LocalDateTime last_updated_at) {

}
