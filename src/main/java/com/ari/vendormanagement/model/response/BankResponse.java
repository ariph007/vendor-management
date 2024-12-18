package com.ari.vendormanagement.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {
  private String id;
  private String name;
  private String code;
  private String transferCode;
  private String description;
  private Long version;
}
