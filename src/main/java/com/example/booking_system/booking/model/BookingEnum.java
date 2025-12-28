package com.example.booking_system.booking.model;

public class BookingEnum {
    
    public enum BookingStatus{
        DRAFT("DRAFT"),
        ACTIVE("ACTIVE"),
        COMPLETED("COMPLETED"),
        UNPAID("UNPAID"),
        PAID("PAID");

        private final String bookingStatus;

        BookingStatus(String bookingStatus){
            this.bookingStatus = bookingStatus;
        }

        public String getBookingStatus(){
            return bookingStatus;
        }
    }
}
