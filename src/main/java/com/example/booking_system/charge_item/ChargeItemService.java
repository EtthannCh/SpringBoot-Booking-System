package com.example.booking_system.charge_item;

import java.util.Optional;

import com.example.booking_system.charge_item.model.ChargeItemCrudDto;
import com.example.booking_system.charge_item.model.ChargeItemDto;
import com.example.booking_system.header.HeaderCollections;

public interface ChargeItemService {
    public Long createChargeItem(ChargeItemCrudDto ciCrud, HeaderCollections header) throws Exception;

    public Optional<ChargeItemDto> findChargeItemByBookingId(Long bookingId);

    public void cancelChargeItem(ChargeItemCrudDto ciCrud, HeaderCollections header) throws Exception;
}
