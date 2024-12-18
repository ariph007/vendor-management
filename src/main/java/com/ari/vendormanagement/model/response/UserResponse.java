package com.ari.vendormanagement.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private Long version;
}
