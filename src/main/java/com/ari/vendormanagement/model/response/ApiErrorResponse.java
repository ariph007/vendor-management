package com.ari.vendormanagement.model.response;


import java.time.ZonedDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
  private String message;
  private Map<String, String> errors;
  private int status;
  private ZonedDateTime time;
}
