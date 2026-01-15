package com.example.booking_system.booking;

import java.util.List;

import com.example.booking_system.booking.model.BookingDetailCrudDto;
import com.example.booking_system.booking.model.BookingDetailDto;
import com.example.booking_system.header.HeaderCollections;

public interface BookingDetailService {
    public Long createBookingDetail(BookingDetailCrudDto bookingDetailCrudDto, HeaderCollections header)
            throws Exception;
    
    public List<BookingDetailDto> findBookingDetailListByBookingId(Long bookingId);
}
