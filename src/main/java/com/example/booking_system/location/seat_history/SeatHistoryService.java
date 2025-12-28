package com.example.booking_system.location.seat_history;

import java.util.Optional;

import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryCrudDto;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryDto;

public interface SeatHistoryService {
    
    public Optional<SeatHistoryDto> findSeatHistoryByLocationId(Long locationId);

    public Long processReserveSeat(SeatHistoryCrudDto seatHistoryCrudDto, HeaderCollections header) throws BusinessException;
}
