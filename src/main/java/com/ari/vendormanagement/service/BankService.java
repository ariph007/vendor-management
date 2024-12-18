package com.ari.vendormanagement.service;

import com.ari.vendormanagement.model.request.CreateBankRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateBankRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.persistence.entity.Bank;
import java.util.List;

public interface BankService {
  BankResponse findById(String id);
  Responses<List<BankResponse>> findAll(PagingRequest pagingRequest);
  void edit(UpdateBankRequest updateBankRequest);
  void deleteById(String id);
  void add(CreateBankRequest createBankRequest);
  Bank getById(String id);
  boolean existsById(String id);
}
