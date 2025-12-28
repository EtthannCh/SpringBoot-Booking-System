package com.example.booking_system.location.location.model;

public class LocationEnum {

    public enum RoomLevel {
        VIP("VIP"),
        NORMAL("NORMAL"),
        NORMAL_PLUS("NORMAL-PLUS");

        private final String roomLevel;

        RoomLevel(String roomLevel) {
            this.roomLevel = roomLevel;
        }

        public String getRoomLevel() {
            return roomLevel;
        }
    }

    public enum LocationType {
        STD("STUDIO"),
        SEC("SECTIONS"),
        ROW("ROWS"),
        STS("SEATS");

        private final String locationType;

        LocationType(String locationType) {
            this.locationType = locationType;
        }

        public String getLocationType() {
            return locationType;
        }
    }
}
