package com.example.booking_system.event.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
    private Long id;
    private String name;
    private Long locationId;
    private String locationName;
    private String description;
    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    private String duration;
    private List<String> startTime;
    private LocalDateTime createdAt;
    private String createdBy;
    private UUID createdById;
    private LocalDateTime lastUpdatedAt;
    private String lastUpdatedBy;
    private UUID lastUpdatedById;
}
