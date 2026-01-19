package com.example.booking_system.charge_item;

import com.example.booking_system.charge_item.model.ChargeItemCrudDto;
import com.example.booking_system.header.HeaderCollections;

public interface ChargeItemService {
    public Long createChargeItem(ChargeItemCrudDto ciCrud, HeaderCollections header) throws Exception;
}
