package com.ari.vendormanagement.service.impl;

import com.ari.vendormanagement.helper.CustomResponseException;
import com.ari.vendormanagement.model.request.CreateVendorBankRequest;
import com.ari.vendormanagement.model.request.CreateVendorRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateVendorBankRequest;
import com.ari.vendormanagement.model.request.UpdateVendorRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.PageResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.VendorBankResponse;
import com.ari.vendormanagement.persistence.entity.Bank;
import com.ari.vendormanagement.persistence.entity.Vendor;
import com.ari.vendormanagement.persistence.entity.VendorBank;
import com.ari.vendormanagement.persistence.repository.VendorBankRepository;
import com.ari.vendormanagement.service.BankService;
import com.ari.vendormanagement.service.VendorBankService;
import com.ari.vendormanagement.service.VendorService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorBankServiceImpl implements VendorBankService {
  private final VendorBankRepository repository;
  private final BankService bankService;
  private final VendorService vendorService;

  @Override
  public VendorBankResponse findById(String id) {
    return mappingToResponse(getById(id));
  }

  @Override
  public Responses<List<VendorBankResponse>> findAll(PagingRequest pagingRequest) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
    Page<VendorBank> vendorBanks = repository.findAll(pageable);
    List<VendorBank> listOfVendorBank = vendorBanks.getContent();
    List<VendorBankResponse> content = listOfVendorBank.stream().map(item -> mappingToResponse(item)).toList();
    Responses<List<VendorBankResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(vendorBanks.getNumber()).pageSize(
            vendorBanks.getSize()).totalElements(vendorBanks.getTotalElements()).totalPages(vendorBanks.getTotalPages())
        .last(vendorBanks.isLast()).build());
    return response;
  }

  @Override
  public void edit(UpdateVendorBankRequest updateVendorBankRequest) {
    VendorBank vendorBank = getById(updateVendorBankRequest.getId());
    BeanUtils.copyProperties(updateVendorBankRequest, vendorBank);

    vendorBank.setBank(bankService.getById(updateVendorBankRequest.getBankId()));
    vendorBank.setVendor(vendorService.getById(updateVendorBankRequest.getVendorId()));
    repository.saveAndFlush(vendorBank);
  }

  @Override
  public void deleteById(String id) {
    repository.delete(getById(id));
  }

  @Override
  public void add(CreateVendorBankRequest createVendorBankRequest) {
    validateAccountNo(createVendorBankRequest.getAccountNo());
    VendorBank vendorBank = new VendorBank();
    BeanUtils.copyProperties(createVendorBankRequest, vendorBank);

    vendorBank.setBank(bankService.getById(createVendorBankRequest.getBankId()));
    vendorBank.setVendor(vendorService.getById(createVendorBankRequest.getVendorId()));
    repository.saveAndFlush(vendorBank);

  }

  @Override
  public VendorBank getById(String id) {
    return repository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "vendor bank is not exist")
    );
  }

  @Override
  public boolean existsById(String id) {
    return repository.existsById(id);
  }

  @Override
  public boolean existsByVendorId(String id) {
    return !repository.findByVendorId(id).isEmpty();
  }

  @Override
  public boolean existsByBankId(String id) {
    return !repository.findByBankId(id).isEmpty();
  }

  private void validateAccountNo(String accountNo){
    if (repository.existsByAccountNo(accountNo)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "account number already exist.");
    }
  }

  private VendorBankResponse mappingToResponse(VendorBank vendorBank){
    VendorBankResponse response = new VendorBankResponse();
    BeanUtils.copyProperties(vendorBank, response);
    response.setBankId(vendorBank.getBank().getId());
    response.setBankName(vendorBank.getBank().getName());
    response.setVendorId(vendorBank.getVendor().getId());
    response.setVendorName(vendorBank.getVendor().getName());
    return response;
  }
}
