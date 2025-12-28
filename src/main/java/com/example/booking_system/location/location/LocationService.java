package com.example.booking_system.location.location;

import java.util.Optional;

import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.model.LocationCrudDto;
import com.example.booking_system.location.location.model.LocationDto;
import com.example.booking_system.location.location.model.LocationEnum.LocationType;

public interface LocationService {

    public Long createLocation(LocationCrudDto locCrud, HeaderCollections header) throws Exception;

    public Optional<LocationDto> findLocationById(Long id);

    public Optional<LocationDto> checkPartOfIsSuitable(Long id, LocationType locationType);
}
