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
public class CreateVendorBankRequest {
  @NotBlank(message = "bank id can't be blank")
  @NotNull(message = "bank id can't be empty")
  private String bankId;

  @NotBlank(message = "vendor id can't be blank")
  @NotNull(message = "vendor id can't be empty")
  private String vendorId;

  @NotBlank(message = "account no can't be blank")
  @NotNull(message = "account no can't be empty")
  private String accountNo;

  @NotBlank(message = "account name can't be blank")
  @NotNull(message = "account name can't be empty")
  private String accountName;

}
