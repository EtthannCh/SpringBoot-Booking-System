package com.example.booking_system.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.booking_system.event.model.EventCrudDto;
import com.example.booking_system.event.model.EventDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.LocationService;
import com.example.booking_system.location.location.model.LocationDto;
import com.example.booking_system.utils.CheckUtil;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationService locationService;

    private Map<String, String> duplicateKeyMap = new HashMap<String, String>(Map.ofEntries(
            Map.entry("events_name_key", "BOK_EVENT_NAMEEXIST")));

    public EventServiceImpl(
            EventRepository eventRepository,
            LocationService locationService) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
    }

    @Override
    public Optional<EventDto> findEventById(Long eventId) {
        return eventRepository.findEventById(eventId);
    }

    @Override
    public Long createEvent(EventCrudDto eventCrudDto, HeaderCollections header) throws BusinessException {
        try {
            LocationDto location = locationService.findLocationById(header.getLocationId())
                    .orElseThrow(() -> new BusinessException("BOK_EVENT_LOCATIONNOTFOUND"));

            if (eventCrudDto.getStartTime().isEmpty())
                throw new BusinessException("BOK_EVENT_STARTTIMEEMPTY");

            if (eventCrudDto.getPeriodStart() == null || eventCrudDto.getPeriodEnd() == null)
                throw new BusinessException("BOK_EVENT_PERIODEMPTY");

            if (eventCrudDto.getPeriodEnd().isBefore(eventCrudDto.getPeriodStart()))
                throw new BusinessException("BOK_EVENT_PERIODENDLESSTHANPERIODSTART");

            if (eventCrudDto.getPeriodStart().isAfter(eventCrudDto.getPeriodEnd()))
                throw new BusinessException("BOK_EVENT_PERIODSTARTGREATERTHANPERIODEND");

            header.setLocationName(location.getName());
            return eventRepository.createEvent(eventCrudDto.toRecord(header));
        } catch (DuplicateKeyException e) {
            CheckUtil.throwUniqueException(e, duplicateKeyMap);
            throw e;
        }
    }

    public List<EventDto> findListEvent(Long categoryId) {
        return eventRepository.findEventList(categoryId);
    }
}
