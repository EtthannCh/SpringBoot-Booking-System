package com.example.booking_system.sequence.model;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.sequence.model.SequenceEnum.SequenceResetCondition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SequenceCrudDto {
    private String name;
    private String format;
    private Integer currentNumber;
    private Integer startNo;
    private SequenceResetCondition resetCondition;

    public Sequence toRecord(HeaderCollections header) {
        return new Sequence(null, name, format, currentNumber, startNo, resetCondition, header.getUserId(),
                header.getUserName(), null, header.getUserId(), header.getUserName(), null);
    }
}
