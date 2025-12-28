package com.example.booking_system.location.seat_history.model.seat_history;

import java.time.LocalDate;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryEnum.SeatHistoryStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatHistoryCrudDto {
    private String code;
    private SeatHistoryStatus status;
    private Long locationId;
    private LocalDate showTime;

    public SeatHistory toRecord(HeaderCollections header) {
        return new SeatHistory(null, code, status, locationId, null, header.getUserName(), header.getUserId(), null,
                header.getUserName(), header.getUserId());
    }
}
