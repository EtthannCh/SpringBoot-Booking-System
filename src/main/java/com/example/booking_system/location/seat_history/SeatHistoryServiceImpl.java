package com.example.booking_system.location.seat_history;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryCrudDto;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryDto;

@Service
public class SeatHistoryServiceImpl implements SeatHistoryService {

    private final SeatHistoryRepository seatHistoryRepository;

    public SeatHistoryServiceImpl(SeatHistoryRepository seatHistoryRepository) {
        this.seatHistoryRepository = seatHistoryRepository;
    }

    @Override
    public Optional<SeatHistoryDto> findSeatHistoryByLocationId(Long locationId) {
        return seatHistoryRepository.findSeatHistoryById(locationId);
    }

    public Long processReserveSeat(SeatHistoryCrudDto seatHistoryCrudDto, HeaderCollections header) {
        return seatHistoryRepository.processReserveSeat(seatHistoryCrudDto.toRecord(header));
    }

    @Override
    public void resetReservedSeats() throws Exception {
        seatHistoryRepository.resetReservedSeats();
    }

    @Override
    public List<SeatHistoryDto> findInvalidSeatHistory(List<Long> seatIds) {
        return seatHistoryRepository.findInvalidSeatHistory(seatIds);
    }

    @Override
    public List<SeatHistoryDto> findSeatHistoryByListId(List<Long> seatIds) {
        return seatHistoryRepository.findSeatHistoryByListId(seatIds);
    }

    @Override
    public void resetReservedSeatPerBooking(List<Long> seatLocationIds) throws Exception {
        seatHistoryRepository.resetReservedSeatsByIdList(seatLocationIds);
    }

}
