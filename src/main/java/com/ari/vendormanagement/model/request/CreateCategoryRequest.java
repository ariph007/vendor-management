package com.ari.vendormanagement.model.request;

import com.ari.vendormanagement.converter.ToUpperCase;
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
public class CreateCategoryRequest {
  @NotBlank(message = "name can't be blank")
  @NotNull(message = "name can't be empty")
  private String name;

  @NotBlank(message = "code can't be blank")
  @NotNull(message = "code can't be empty")
  @ToUpperCase
  private String code;

  private String description;
}
