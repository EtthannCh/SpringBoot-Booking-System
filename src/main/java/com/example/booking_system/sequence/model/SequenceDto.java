package com.example.booking_system.sequence.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.booking_system.sequence.model.SequenceEnum.SequenceResetCondition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SequenceDto {
    private Long id;
    private String name;
    private String format;
    private Integer currentNumber;
    private Integer startNo;
    private SequenceResetCondition resetCondition;
    private UUID createdById;
    private String createdBy;
    private LocalDateTime createdAt;
    private UUID lastUpdatedById;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;

    public SequenceDto fromRecord(Sequence sequence) {
        if (sequence == null)
            return null;

        return new SequenceDto()
                .setId(sequence.id())
                .setName(sequence.name())
                .setFormat(sequence.format())
                .setCurrentNumber(sequence.current_number())
                .setStartNo(sequence.start_no())
                .setResetCondition(sequence.reset_condition())
                .setCreatedById(sequence.created_by_id())
                .setCreatedBy(sequence.created_by())
                .setCreatedAt(sequence.created_at())
                .setLastUpdatedById(sequence.last_updated_by_id())
                .setLastUpdatedBy(sequence.last_updated_by())
                .setLastUpdatedAt(sequence.last_updated_at());

    }
}
