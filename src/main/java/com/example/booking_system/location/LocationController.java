package com.example.booking_system.location;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.LocationService;
import com.example.booking_system.location.location.model.LocationCrudDto;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Long ceate(
            @RequestBody LocationCrudDto loc,
            @RequestHeader(value = "user-id") UUID userId,
            @RequestHeader(value = "user-name") String userName) throws Exception {
        HeaderCollections header = new HeaderCollections()
                .setUserId(userId)
                .setUserName(userName);

        return locationService.createLocation(loc, header);
    }

}
