package com.ari.vendormanagement.service;

import com.ari.vendormanagement.model.request.CreateBankRequest;
import com.ari.vendormanagement.model.request.CreateVendorBankRequest;
import com.ari.vendormanagement.model.request.CreateVendorRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateBankRequest;
import com.ari.vendormanagement.model.request.UpdateVendorBankRequest;
import com.ari.vendormanagement.model.request.UpdateVendorRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.VendorBankResponse;
import com.ari.vendormanagement.persistence.entity.Bank;
import com.ari.vendormanagement.persistence.entity.VendorBank;
import java.util.List;

public interface VendorBankService {
  VendorBankResponse findById(String id);
  Responses<List<VendorBankResponse>> findAll(PagingRequest pagingRequest);
  void edit(UpdateVendorBankRequest updateVendorBankRequest);
  void deleteById(String id);
  void add(CreateVendorBankRequest createVendorBankRequest);
  VendorBank getById(String id);
  boolean existsById(String id);
  boolean existsByVendorId(String id);
  boolean existsByBankId(String id);
}
