package com.example.booking_system.event;

import java.util.List;
import java.util.Optional;

import com.example.booking_system.event.model.EventCrudDto;
import com.example.booking_system.event.model.EventDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;

public interface EventService {
    public Optional<EventDto> findEventById(Long eventId);

    public Long createEvent(EventCrudDto eventCrudDto, HeaderCollections header) throws BusinessException;

    public List<EventDto> findListEvent(Long categoryId);
}
