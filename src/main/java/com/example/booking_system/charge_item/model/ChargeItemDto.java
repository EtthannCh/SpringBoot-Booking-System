package com.example.booking_system.charge_item.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.booking_system.charge_item.model.ChargeItemEnum.ChargeItemStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeItemDto {
    private Long id;
    private Long bookingId;
    private ChargeItemStatus chargeItemStatus;
    private Double qty;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private UUID createdById;
    private String createdBy;
    private LocalDateTime createdAt;
    private UUID lastUpdatedById;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;

    public ChargeItemDto fromRecord(ChargeItem ci){
        if(ci == null) return null;

        return new ChargeItemDto()
            .setId(ci.id())
            .setBookingId(ci.booking_id())
            .setChargeItemStatus(ci.charge_item_status())
            .setQty(ci.qty())
            .setUnitPrice(ci.unit_price())
            .setTotalPrice(ci.total_price())
            .setCreatedById(ci.created_by_id())
            .setCreatedBy(ci.created_by())
            .setCreatedAt(ci.created_at())
            .setLastUpdatedById(ci.last_updated_by_id())
            .setLastUpdatedBy(ci.last_updated_by())
            .setLastUpdatedAt(ci.last_updated_at());
    }
}
