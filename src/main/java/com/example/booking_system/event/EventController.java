package com.example.booking_system.event;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_system.constant.HeaderConstants;
import com.example.booking_system.event.model.EventCrudDto;
import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createEvent(
            @RequestBody EventCrudDto eventCrudDto,
            @RequestHeader(HeaderConstants.USER_ID) UUID userId,
            @RequestHeader(HeaderConstants.USER_NAME) String userName,
            @RequestHeader(HeaderConstants.LOCATION_ID) Long locationId,
            @RequestHeader(HeaderConstants.LOCATION_NAME) String locationName
            ) throws BusinessException {
        HeaderCollections header = new HeaderCollections()
                .setUserId(userId)
                .setUserName(userName)
                .setLocationId(locationId)
                .setLocationName(locationName)
                ;
        return eventService.createEvent(eventCrudDto, header);
    }

}
