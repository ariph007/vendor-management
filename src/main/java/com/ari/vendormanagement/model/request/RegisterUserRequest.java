package com.ari.vendormanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
  @NotBlank(message = "first name can't be blank")
  @NotNull(message = "first name can't be empty")
  private String firstName;

  private String lastName;

  @NotBlank(message = "username name can't be blank")
  @NotNull(message = "username name can't be empty")
  private String username;

  @NotBlank(message = "email name can't be blank")
  @NotNull(message = "email name can't be empty")
  private String email;

  @NotBlank(message = "password name can't be blank")
  @NotNull(message = "password name can't be empty")
  private String password;

  @NotBlank(message = "role id can't be blank")
  @NotNull(message = "role id name can't be empty")
  private String roleId;
}
