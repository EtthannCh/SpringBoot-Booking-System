package com.example.booking_system.booking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDetailDto {

    private Long id;
    private Long bookingId;
    private Double price;
    private List<Long> seatIds;
    private LocalDateTime createdAt;
    private String createdBy;
    private UUID createdById;

    public static BookingDetailDto fromRecord(BookingDetail bookingDetail) {
        if (bookingDetail == null)
            return null;

        List<Long> seatIdList = Arrays.asList(bookingDetail.seat_id());
        return new BookingDetailDto()
                .setId(bookingDetail.id())
                .setBookingId(bookingDetail.booking_id())
                .setPrice(bookingDetail.price())
                .setSeatIds(seatIdList)
                .setCreatedAt(bookingDetail.created_at())
                .setCreatedBy(bookingDetail.created_by())
                .setCreatedById(bookingDetail.created_by_id());
    }

    public static List<BookingDetailDto> fromRecordList(List<BookingDetail> bdList) {
        if (bdList.isEmpty())
            return Collections.emptyList();

        List<BookingDetailDto> bookingList = new ArrayList<>();
        for (BookingDetail bookingDetail : bdList) {
            bookingList.add(fromRecord(bookingDetail));
        }
        return bookingList;
    }
}
