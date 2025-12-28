package com.example.booking_system.booking;

import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.header.HeaderCollections;

public interface BookingService {
    
    public Long createBooking(BookingCrudDto bookingCrudDto, HeaderCollections header) throws Exception;
}
