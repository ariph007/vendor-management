package com.ari.vendormanagement.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest extends CreateCategoryRequest{
  @NotNull(message = "id is required.")
  private String id;

  @NotNull(message = "version is required")
  private Long version;
}
