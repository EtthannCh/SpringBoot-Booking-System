package com.example.booking_system.booking;

import com.example.booking_system.booking.model.BookingDetailCrudDto;
import com.example.booking_system.header.HeaderCollections;

public interface BookingDetailService {
    public Long createBookingDetail(BookingDetailCrudDto bookingDetailCrudDto, HeaderCollections header)
            throws Exception;
}
