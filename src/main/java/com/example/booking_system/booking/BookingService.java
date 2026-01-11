package com.example.booking_system.booking;

import java.util.List;

import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.booking.model.BookingDto;
import com.example.booking_system.header.HeaderCollections;

public interface BookingService {

    public Long createBooking(BookingCrudDto bookingCrudDto, HeaderCollections header) throws Exception;

    public void cancelBooking(Long bookingId, HeaderCollections header) throws Exception;

    public void cancelBookingByScheduler(Long bookingId) throws Exception;

    public List<BookingDto> findExpiredBookingList();
}
