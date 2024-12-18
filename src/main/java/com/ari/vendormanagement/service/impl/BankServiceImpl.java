package com.ari.vendormanagement.service.impl;

import com.ari.vendormanagement.helper.CustomResponseException;
import com.ari.vendormanagement.model.request.CreateBankRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateBankRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.PageResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.persistence.entity.Bank;
import com.ari.vendormanagement.persistence.repository.BankRepository;
import com.ari.vendormanagement.service.BankService;
import com.ari.vendormanagement.service.VendorBankService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

  private final BankRepository repository;
  @Setter(onMethod_ = @Autowired, onParam_ = @Lazy)
  private VendorBankService vendorBankService;


  @Override
  public BankResponse findById(String id) {
    return mappingToResponse(getById(id));
  }

  @Override
  public Responses<List<BankResponse>> findAll(PagingRequest pagingRequest) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
    Page<Bank> banks = repository.findAll(pageable);
    List<Bank> listOfBank = banks.getContent();
    List<BankResponse> content = listOfBank.stream().map(item -> mappingToResponse(item)).toList();
    Responses<List<BankResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(banks.getNumber()).pageSize(
            banks.getSize()).totalElements(banks.getTotalElements()).totalPages(banks.getTotalPages())
        .last(banks.isLast()).build());
    return response;
  }

  @Override
  public void edit(UpdateBankRequest updateBankRequest) {
    Bank bank = getById(updateBankRequest.getId());
    BeanUtils.copyProperties(updateBankRequest, bank);
    repository.saveAndFlush(bank);
  }

  @Override
  public void deleteById(String id) {
    validateBankIsUsed(id);
    repository.delete(getById(id));
  }

  @Override
  public void add(CreateBankRequest createBankRequest) {
    validateTransferCode(createBankRequest.getTransferCode());
    validateBK(createBankRequest.getTransferCode(), createBankRequest.getCode());
    Bank bank = new Bank();
    BeanUtils.copyProperties(createBankRequest, bank);
    repository.saveAndFlush(bank);
  }

  @Override
  public Bank getById(String id) {
    return repository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "bank is not exist")
    );
  }

  @Override
  public boolean existsById(String id) {
    return repository.existsById(id);
  }

  private BankResponse mappingToResponse(Bank bank) {
    BankResponse response = new BankResponse();
    BeanUtils.copyProperties(bank, response);
    return response;
  }

  private void validateTransferCode(String transferCode) {
    if (transferCode.length() != 3) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "transfer code must be 3 characters long.");
    }
    if (!transferCode.matches("\\d+")) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "transfer code must consist of numbers only");
    }
  }

  private void validateBK(String transferCode, String code) {
    if (repository.existsByTransferCode(transferCode)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "transfer code already exist.");
    }
    if (repository.existsByCode(code)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "code already exist.");
    }
  }

  private void validateBankIsUsed(String bankId){
    if (vendorBankService.existsByBankId(bankId)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "bank is used in vendor bank account.");
    }
  }

}
