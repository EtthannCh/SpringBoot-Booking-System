package com.example.booking_system.charge_item.model;

import java.math.BigDecimal;

import com.example.booking_system.header.HeaderCollections;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeItemCrudDto {
    private Long bookingId;
    private Double qty;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public ChargeItem toRecord(HeaderCollections header) {
        return new ChargeItem(null, bookingId, qty, unitPrice, totalPrice, header.getUserId(), header.getUserName(),
                null, header.getUserId(), header.getUserName(), null);
    }
}
