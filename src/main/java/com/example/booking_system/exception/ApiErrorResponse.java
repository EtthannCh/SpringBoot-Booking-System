package com.example.booking_system.exception;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiErrorResponse {
    public int statusCode;
    public String errorCode;
    public Set<String> details;
}
