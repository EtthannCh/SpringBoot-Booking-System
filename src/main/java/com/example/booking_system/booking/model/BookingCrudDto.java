package com.example.booking_system.booking.model;

import java.time.LocalTime;

import com.example.booking_system.booking.model.BookingEnum.BookingStatus;
import com.example.booking_system.header.HeaderCollections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingCrudDto {
    private Long eventId;
    private BookingStatus status;
    private String bookingNo;
    private Double qty;
    private BookingDetailCrudDto bookingDetailCrudDto;
    private LocalTime showTime;

    public Booking toRecord(BookingCrudDto bCrudDto, HeaderCollections header) {
        if (bCrudDto == null)
            return null;

        return new Booking(null, header.getUserId(), eventId, status, bookingNo, showTime ,qty, null, header.getUserName(),
                header.getUserId(), null, header.getUserName(), header.getUserId());
    }
}
