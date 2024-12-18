package com.ari.vendormanagement.service;

import com.ari.vendormanagement.model.request.CreateVendorRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateVendorRequest;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.VendorResponse;
import com.ari.vendormanagement.persistence.entity.Bank;
import com.ari.vendormanagement.persistence.entity.Vendor;
import java.util.List;

public interface VendorService {
  VendorResponse findById(String id);
  Responses<List<VendorResponse>> findAll(PagingRequest pagingRequest);
  void edit(UpdateVendorRequest updateVendorRequest);
  void deleteById(String id);
  void add(CreateVendorRequest createVendorRequest);
  Vendor getById(String id);
  boolean existsById(String id);
}
