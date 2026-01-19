package com.example.booking_system.charge_item;

import org.springframework.stereotype.Service;

import com.example.booking_system.charge_item.model.ChargeItemCrudDto;
import com.example.booking_system.header.HeaderCollections;

@Service
public class ChargeItemServiceImpl implements ChargeItemService {

    private final ChargeItemRepository chargeItemRepository;

    public ChargeItemServiceImpl(ChargeItemRepository chargeItemRepository) {
        this.chargeItemRepository = chargeItemRepository;
    }

    @Override
    public Long createChargeItem(ChargeItemCrudDto ciCrud, HeaderCollections header) throws Exception {
        return chargeItemRepository.create(ciCrud.toRecord(header));
    }

}
