package com.example.booking_system.charge_item.model;

public class ChargeItemEnum {

    public enum ChargeItemStatus {
        BILLABLE("BILLABLE"),
        BILLED("BILLED"),
        ABORTED("ABORTED"),
        REFUNDED("REFUNDED");

        private final String chargeItemStatus;

        ChargeItemStatus(String chargeItemStatus) {
            this.chargeItemStatus = chargeItemStatus;
        }

        public String getStatus() {
            return chargeItemStatus;
        }
    }
}
