package com.example.booking_system.location.seat_history.model.seat_history;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryEnum.SeatHistoryStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatHistoryDto {
    private Long id;
    private String code;
    private SeatHistoryStatus status;
    private Long locationId;
    private LocalDateTime createdAt;
    private String createdBy;
    private UUID createdById;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;
    private UUID lastUpdatedById;

    public SeatHistoryDto fromRecord(SeatHistory seatHistory) {
        if (seatHistory == null)
            return null;

        return new SeatHistoryDto()
                .setId(seatHistory.id())
                .setCode(seatHistory.code())
                .setStatus(seatHistory.status())
                .setLocationId(seatHistory.location_id())
                .setCreatedAt(seatHistory.created__at())
                .setCreatedBy(seatHistory.created_by())
                .setCreatedById(seatHistory.created_by_id())
                .setLastUpdatedAt(seatHistory.last_updated__at())
                .setLastUpdatedBy(seatHistory.last_updated_by())
                .setLastUpdatedById(seatHistory.last_updated_by_id());
    }
}
