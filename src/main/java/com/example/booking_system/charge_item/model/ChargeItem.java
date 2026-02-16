package com.example.booking_system.charge_item.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.example.booking_system.charge_item.model.ChargeItemEnum.ChargeItemStatus;

public record ChargeItem(
        @Id Long id,
        Long booking_id,
        ChargeItemStatus charge_item_status,
        Double qty,
        BigDecimal unit_price,
        BigDecimal total_price,
        UUID created_by_id,
        String created_by,
        LocalDateTime created_at,
        UUID last_updated_by_id,
        String last_updated_by,
        LocalDateTime last_updated_at) {

}
