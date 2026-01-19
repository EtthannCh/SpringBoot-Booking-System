package com.example.booking_system.booking;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.booking.model.BookingDetailDto;
import com.example.booking_system.header.HeaderCollections;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final BookingDetailService bookingDetailService;

    public BookingController(BookingService bookingService, BookingDetailService bookingDetailService) {
        this.bookingService = bookingService;
        this.bookingDetailService = bookingDetailService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createBookings(
            @RequestBody BookingCrudDto bookingCrudDto,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "user-id") UUID userId) throws Exception {
        HeaderCollections header = new HeaderCollections()
                .setUserId(userId)
                .setUserName(userName);
        return bookingService.createBooking(bookingCrudDto, header);
    }

    @PostMapping("/cancel-booking")
    public void cancelBooking(
            @RequestParam Long bookingId,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "user-id") UUID userId) throws Exception {
        HeaderCollections header = new HeaderCollections().setUserId(userId).setUserName(userName);
        bookingService.cancelBooking(bookingId, header);
    };

    @GetMapping("/find-booking-detail-list")
    public Optional<BookingDetailDto> tes(@RequestParam Long bookingId) {
        return bookingDetailService.findBookingDetailListByBookingId(bookingId);
    }

    @PutMapping("/confirm-booking/{id}")
    public void confirmBooking(@PathVariable Long id,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "user-id") UUID userId) throws Exception {
        HeaderCollections header = new HeaderCollections().setUserId(userId).setUserName(userName);
        bookingService.confirmBookingWithPayment(id, header);
    }
}
