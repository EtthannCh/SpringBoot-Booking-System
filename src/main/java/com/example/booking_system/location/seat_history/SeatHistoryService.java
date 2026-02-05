package com.example.booking_system.location.seat_history;

import java.util.List;
import java.util.Optional;

import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryCrudDto;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryDto;

public interface SeatHistoryService {

    public Optional<SeatHistoryDto> findSeatHistoryByLocationId(Long locationId);

    public Long processReserveSeat(SeatHistoryCrudDto seatHistoryCrudDto, HeaderCollections header)
            throws BusinessException;

    public void resetReservedSeats() throws Exception;

    public void resetReservedSeatPerBooking(List<Long> seatLocationIds) throws Exception;

    public List<SeatHistoryDto> findSeatHistoryByListId(List<Long> seatIds);

    public List<SeatHistoryDto> findInvalidSeatHistory(List<Long> seatIds);

    public void updateSeatToOccupied(Long seatId, HeaderCollections header);
}
