package com.example.booking_system.booking;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.booking_system.booking.model.BookingCrudDto;
import com.example.booking_system.booking.model.BookingDetailDto;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.qr_generator.QrGeneratorService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final BookingDetailService bookingDetailService;

    @Autowired
    private final QrGeneratorService qrGeneratorService;

    public BookingController(
            BookingService bookingService,
            BookingDetailService bookingDetailService,
            QrGeneratorService qrGeneratorService) {
        this.bookingService = bookingService;
        this.bookingDetailService = bookingDetailService;
        this.qrGeneratorService = qrGeneratorService;
    }

    @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<byte[]> createBookings(
            @RequestBody BookingCrudDto bookingCrudDto,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "user-id") UUID userId) throws Exception {
        HeaderCollections header = new HeaderCollections()
                .setUserId(userId)
                .setUserName(userName);
        Long id = bookingService.createBooking(bookingCrudDto, header);
        return ResponseEntity.ok(qrGeneratorService
                .generateImageQRCode(id.toString(), 200, 200));
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

    @PostMapping(value = "/confirm-booking-qr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> confirmBookingUsingQrCode(
            @RequestBody MultipartFile file,
            @RequestHeader(name = "user-name") String userName,
            @RequestHeader(name = "user-id") UUID userId) throws Exception {
        Map<String, String> response = new HashMap<>();
        try {
            HeaderCollections header = new HeaderCollections().setUserId(userId).setUserName(userName);
            String decodedText = qrGeneratorService.decodeQrCode(file);
            response.put("decodedText", decodedText);
            bookingService.confirmBookingWithPayment(Long.valueOf(decodedText), header);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to decode QR code.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
