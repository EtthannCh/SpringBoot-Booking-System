package com.example.booking_system.booking.model;

import com.example.booking_system.header.HeaderCollections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDetailCrudDto {
    private Long bookingId;
    private Double price;
    private Long seatId;

    public BookingDetail toRecord(HeaderCollections headers) {
        return new BookingDetail(null, bookingId, price, seatId, headers.getUserId(), headers.getUserName(), null);
    }
}
