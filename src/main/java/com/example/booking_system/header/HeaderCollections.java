package com.example.booking_system.header;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeaderCollections {
    private UUID userId;
    private String userName;
    private Long locationId;
    private String locationName;
}
