package com.example.booking_system.sequence;

import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.sequence.model.SequenceCrudDto;

public interface SequenceService {
    public Long createSequence(SequenceCrudDto sqCrud, HeaderCollections header) throws Exception;

    public String generateSequenceNo(String sequenceName, HeaderCollections header) throws BusinessException;
}
