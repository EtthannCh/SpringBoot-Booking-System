package com.example.booking_system.scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.booking_system.booking.BookingDetailService;
import com.example.booking_system.booking.BookingService;
import com.example.booking_system.booking.model.BookingDetailDto;
import com.example.booking_system.booking.model.BookingDto;
import com.example.booking_system.location.seat_history.SeatHistoryService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableScheduling
public class Scheduler {

    @Autowired
    private SeatHistoryService seatHistoryService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDetailService bookingDetailService;

    // run scheduler every day at 11PM
    @Scheduled(cron = "0 0 23 * * *")
    void executeFixedDelayTask() throws InterruptedException, Exception {
        log.info("Reset Reseved Seats at 11PM");
        seatHistoryService.resetReservedSeats();
    }

    @Scheduled(fixedDelay = 600000)
    void cancelExpiredBookings() throws Exception {
        List<BookingDto> expiredBooking = bookingService.findExpiredBookingList();
        for (BookingDto bookingDto : expiredBooking) {
            bookingService.cancelBookingByScheduler(bookingDto.getId());
            List<BookingDetailDto> bookingList = bookingDetailService
                    .findBookingDetailListByBookingId(bookingDto.getId());
            List<Long> seatIds = bookingList.stream().flatMap(dto -> dto.getSeatIds().stream()).toList();
            seatHistoryService.resetReservedSeatPerBooking(seatIds);
        }
        log.info("Cancel Expired Bookings " + LocalDateTime.now().atZone(ZoneId.systemDefault()));
    }
}
