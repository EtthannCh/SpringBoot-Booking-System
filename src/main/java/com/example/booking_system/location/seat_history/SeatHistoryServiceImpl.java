package com.example.booking_system.location.seat_history;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryCrudDto;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryDto;

@Service
public class SeatHistoryServiceImpl implements SeatHistoryService{

    private final SeatHistoryRepository seatHistoryRepository;

    public SeatHistoryServiceImpl(SeatHistoryRepository seatHistoryRepository){
        this.seatHistoryRepository = seatHistoryRepository;
    }

    @Override
    public Optional<SeatHistoryDto> findSeatHistoryByLocationId(Long locationId) {
        return seatHistoryRepository.findSeatHistoryById(locationId);
    }

    public Long processReserveSeat(SeatHistoryCrudDto seatHistoryCrudDto, HeaderCollections header){
        return seatHistoryRepository.processReserveSeat(seatHistoryCrudDto.toRecord(header));
    }
    
}
