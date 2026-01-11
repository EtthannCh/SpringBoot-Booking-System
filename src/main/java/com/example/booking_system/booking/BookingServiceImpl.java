package com.example.booking_system.booking;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.booking_system.auth.role.model.RoleEnum.RoleCodeEnum;
import com.example.booking_system.auth.user_account.UserAccountService;
import com.example.booking_system.auth.user_account.model.UserAccountDto;
import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.booking.model.BookingDetailCrudDto;
import com.example.booking_system.booking.model.BookingDto;
import com.example.booking_system.booking.model.BookingEnum.BookingStatus;
import com.example.booking_system.event.EventService;
import com.example.booking_system.event.model.EventDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.LocationService;
import com.example.booking_system.location.location.model.LocationDto;
import com.example.booking_system.location.location.model.LocationEnum.LocationType;
import com.example.booking_system.location.seat_history.SeatHistoryService;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryCrudDto;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryDto;
import com.example.booking_system.location.seat_history.model.seat_history.SeatHistoryEnum.SeatHistoryStatus;
import com.example.booking_system.sequence.SequenceService;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final LocationService locationService;
    private final SeatHistoryService seatHistoryService;
    private final EventService eventService;
    private final SequenceService sequenceService;
    private final BookingDetailService bookingDetailService;
    private final UserAccountService userAccountService;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            LocationService locationService,
            SeatHistoryService seatHistoryService,
            EventService eventService,
            SequenceService sequenceService,
            BookingDetailService bookingDetailService,
            UserAccountService userAccountService) {
        this.bookingRepository = bookingRepository;
        this.locationService = locationService;
        this.seatHistoryService = seatHistoryService;
        this.eventService = eventService;
        this.sequenceService = sequenceService;
        this.bookingDetailService = bookingDetailService;
        this.userAccountService = userAccountService;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long createBooking(BookingCrudDto bookingCrudDto, HeaderCollections header) throws Exception {
        Set<String> errString = new HashSet<>();
        // create user is mandatory for booking a movie ticket
        UserAccountDto user = userAccountService.findByUserId(header.getUserId())
                .orElseThrow(() -> new BusinessException("BOK_BOOKING_USERNOTFOUND"));
        if (!user.getRoleCode().equals(RoleCodeEnum.USER))
            throw new BusinessException("BOK_BOOKING_USERNOTALLOWED");

        locationService.findLocationById(bookingCrudDto.getBookingDetailCrudDto().getSeatId())
                .orElseThrow(() -> new BusinessException(
                        "BOK_BOOKING_LOCATIONNOTFOUND"));

        EventDto event = eventService.findEventById(bookingCrudDto.getEventId())
                .orElseThrow(() -> new BusinessException("BOK_BOOKINGS_EVENTNOTFOUND"));

        List<String> startTimeList = event.getStartTime();
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("HH:mm");
        for (String startTimeString : startTimeList) {
            LocalTime startTime = LocalTime.parse(startTimeString, sdf);

            if (startTime.equals(bookingCrudDto.getShowTime()))
                break;
            else if (!startTime.equals(bookingCrudDto.getShowTime()))
                throw new BusinessException("BOK_BOOKINGS_INVALIDTIME");
        }

        Double totalBooking = bookingRepository.findBookingCountForSingleEventPerPeriod(
                bookingCrudDto.getEventId(),
                bookingCrudDto.getShowTime());
        if (totalBooking > 100)
            throw new BusinessException("BOK_BOOKINGS_NOAVAILABLESEATS");

        String sequence = sequenceService.generateSequenceNo("BOOKING_SEQUENCE", header);
        bookingCrudDto.setBookingNo(sequence);

        if (event.getPeriodEnd() != null && event.getPeriodEnd().compareTo(LocalDateTime.now()) < 0)
            throw new BusinessException("BOK_BOOKINGS_EVENTEXPIRED");

        Optional<SeatHistoryDto> seatHistory = seatHistoryService
                .findSeatHistoryByLocationId(bookingCrudDto.getBookingDetailCrudDto().getSeatId());
        if (seatHistory.isPresent() && EnumSet.of(SeatHistoryStatus.OCCUPIED, SeatHistoryStatus.RESERVED)
                .contains(seatHistory.get().getStatus())) {
            errString.add("BOK_BOOKINGS_SEATUNAVAILABLE");
            throw new BusinessException("BOK_BOOKINGS_CREATEFAILED", errString);
            // TODO:
            // Give a list of show time for the users, then the time that are chosen by the
            // user will be saved into the bookings database
            // for the reset schedule, it will be set at 00:00 (the next day) -> create
            // scheduler to reset the seat every day.
        }

        LocationDto location = locationService
                .findLocationById(bookingCrudDto.getBookingDetailCrudDto().getSeatId())
                .orElseThrow(() -> new BusinessException("BOK_BOOKING_LOCATIONNOTFOUND"));
        if (!location.getLocationType().equals(LocationType.STS))
            throw new BusinessException("BOK_BOOKING_LOCATIONTYPEINVALID");

        SeatHistoryCrudDto seatHistoryCrudDto = new SeatHistoryCrudDto()
                .setCode(String.join("/", location.getSection(), location.getRow(),
                        location.getCol().toString()))
                .setLocationId(location.getId())
                .setStatus(SeatHistoryStatus.RESERVED);
        seatHistoryService.processReserveSeat(seatHistoryCrudDto, header);

        Long bookingId = bookingRepository.create(bookingCrudDto.toRecord(bookingCrudDto, header));

        BookingDetailCrudDto bookingDetailCrudDto = new BookingDetailCrudDto()
                .setBookingId(bookingId)
                .setSeatId(location.getId())
                .setPrice(10D);
        bookingDetailService.createBookingDetail(bookingDetailCrudDto, header);

        return bookingId;
    }

    @Override
    public void cancelBooking(Long bookingId, HeaderCollections header) throws Exception {
        BookingDto booking = bookingRepository.findBookingById(bookingId)
                .orElseThrow(() -> new BusinessException("BOK_CANCELBOOKING_IDNOTFOUND"));
        if (!booking.getStatus().equals(BookingStatus.RESERVED))
            throw new BusinessException("BOK_CANCELBOOKING_INVALIDSTATUS");

        bookingRepository.cancelBooking(bookingId, header);
    }

    @Override
    public void cancelBookingByScheduler(Long bookingId) throws Exception {
        BookingDto booking = bookingRepository.findBookingById(bookingId)
                .orElseThrow(() -> new BusinessException("BOK_CANCELBOOKING_IDNOTFOUND"));
        if (!booking.getStatus().equals(BookingStatus.RESERVED))
            throw new BusinessException("BOK_CANCELBOOKING_INVALIDSTATUS");

        bookingRepository.cancelBookingByScheduler(bookingId);
    }

    @Override
    public List<BookingDto> findExpiredBookingList() {
        return bookingRepository.findExpiredBookings();
    }
}
