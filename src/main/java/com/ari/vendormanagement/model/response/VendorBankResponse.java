package com.ari.vendormanagement.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorBankResponse {
  private String id;
  private String bankId;
  private String bankName;
  private String vendorId;
  private String vendorName;
  private String accountNo;
  private String accountName;
  private Long version;
}
