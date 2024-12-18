package com.ari.vendormanagement.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponse {
  private String id;
  private String name;
  private String code;
  private String address;
  private String email;
  private String categoryId;
  private String categoryName;
  private String description;
  private Long version;
}
