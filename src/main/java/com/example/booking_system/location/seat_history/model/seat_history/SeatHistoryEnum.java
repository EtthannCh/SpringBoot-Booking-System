package com.example.booking_system.location.seat_history.model.seat_history;

public class SeatHistoryEnum {
    public enum SeatHistoryStatus{
        UNOCCUPIED("UNOCCUPIED"),
        OCCUPIED("OCCUPIED"),
        RESERVED("RESERVED")
        ;
        
        private final String seatHistory;

        SeatHistoryStatus(String seatHistory){
            this.seatHistory = seatHistory;
        }

        public String getSeatHistoryStatus(){
            return seatHistory;
        }
    }   
}
