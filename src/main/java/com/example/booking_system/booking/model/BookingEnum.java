package com.example.booking_system.booking.model;

public class BookingEnum {

    public enum BookingStatus {
        RESERVED("RESERVED"),
        COMPLETED("COMPLETED"),
        CANCELLED("CANCELLED");

        private final String bookingStatus;

        BookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }
    }
}
