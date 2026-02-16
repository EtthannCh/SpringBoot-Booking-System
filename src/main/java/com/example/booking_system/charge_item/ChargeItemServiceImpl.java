package com.example.booking_system.charge_item;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.booking_system.charge_item.model.ChargeItemCrudDto;
import com.example.booking_system.charge_item.model.ChargeItemDto;
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

    @Override
    public Optional<ChargeItemDto> findChargeItemByBookingId(Long bookingId) {
        return chargeItemRepository.findByBookingId(bookingId);
    }

    @Override
    public void cancelChargeItem(ChargeItemCrudDto ciCrud, HeaderCollections header) throws Exception {
        chargeItemRepository.cancelChargeItemByBookingId(ciCrud.toRecord(header));
    }

}
