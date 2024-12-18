package com.ari.vendormanagement.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Setter
@Getter
public class CustomResponseException extends RuntimeException {
  private HttpStatus status;
  private String message;

}