package com.example.booking_system.location.location;

import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.booking_system.exception.BusinessException;
import com.example.booking_system.header.HeaderCollections;
import com.example.booking_system.location.location.model.LocationCrudDto;
import com.example.booking_system.location.location.model.LocationDto;
import com.example.booking_system.location.location.model.LocationEnum.LocationType;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Long createLocation(LocationCrudDto locCrud, HeaderCollections header) throws Exception {
        try {
            return locationRepository.create(locCrud.toRecord(locCrud, header));
        } catch (DuplicateKeyException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Optional<LocationDto> checkPartOfIsSuitable(Long id, LocationType locationType) {
        return locationRepository.checkPartOfIsSuitable(id, locationType);
    }

    @Override
    public Optional<LocationDto> findLocationById(Long id) {
        return locationRepository.findLocationById(id);
    }

}
