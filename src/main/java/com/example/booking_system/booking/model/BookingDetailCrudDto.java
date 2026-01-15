package com.example.booking_system.booking.model;

import java.util.List;

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
    private List<Long> seatIds;

    public BookingDetail toRecord(HeaderCollections headers) {
        Long[] seatArr = new Long[seatIds.size()];
        seatArr = seatIds.toArray(seatArr);
        return new BookingDetail(null, bookingId, price, seatArr, headers.getUserId(), headers.getUserName(), null);
    }
}
