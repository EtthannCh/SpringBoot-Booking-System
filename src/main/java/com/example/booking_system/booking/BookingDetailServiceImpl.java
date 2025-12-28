package com.example.booking_system.booking;

import org.springframework.stereotype.Service;

import com.example.booking_system.booking.model.BookingDetailCrudDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;

@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    private final BookingDetailRepository bookingDetailRepository;

    public BookingDetailServiceImpl(BookingDetailRepository bookingDetailRepository) {
        this.bookingDetailRepository = bookingDetailRepository;
    }

    @Override
    public Long createBookingDetail(BookingDetailCrudDto bookingDetailCrudDto, HeaderCollections header)
            throws BusinessException {
        return bookingDetailRepository.create(bookingDetailCrudDto.toRecord(header));
    }

}
