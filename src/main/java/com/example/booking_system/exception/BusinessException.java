package com.example.booking_system.exception;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessException extends RuntimeException {
   public int statusCode;
   public String errorCode;
   public Set<String> details = new HashSet<>();

   public BusinessException(String errorCode) {
      this.errorCode = errorCode;
   }

   public BusinessException(String errorCode, Set<String> details) {
      this.errorCode = errorCode;
      this.details = details;
   }
}
