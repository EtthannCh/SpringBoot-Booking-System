package com.example.booking_system.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.booking_system.location.seat_history.SeatHistoryService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableScheduling
public class Scheduler {

    @Autowired
    private SeatHistoryService seatHistoryService;
    
    // run scheduler every day at 11PM
    @Scheduled(cron = "0 0 23 * * *")
    void executeFixedDelayTask() throws InterruptedException, Exception{
        log.info("Reset Reseved Seats at 11PM");
        seatHistoryService.resetReservedSeats();
    }
}
