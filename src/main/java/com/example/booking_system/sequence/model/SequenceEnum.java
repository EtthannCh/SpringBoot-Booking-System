package com.example.booking_system.sequence.model;

public class SequenceEnum {

    public enum SequenceResetCondition {
        DAY("DAY"),
        MONTH("MONTH"),
        YEAR("YEAR");

        private final String SequenceResetCondition;

        SequenceResetCondition(String SequenceResetCondition) {
            this.SequenceResetCondition = SequenceResetCondition;
        }

        public String getSequenceResetCondition() {
            return SequenceResetCondition;
        }
    }
}
