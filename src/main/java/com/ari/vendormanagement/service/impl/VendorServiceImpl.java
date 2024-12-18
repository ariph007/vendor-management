package com.ari.vendormanagement.service.impl;

import com.ari.vendormanagement.helper.CustomResponseException;
import com.ari.vendormanagement.model.request.CreateVendorRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateVendorRequest;
import com.ari.vendormanagement.model.response.PageResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.VendorResponse;
import com.ari.vendormanagement.persistence.entity.Category;
import com.ari.vendormanagement.persistence.entity.Vendor;
import com.ari.vendormanagement.persistence.repository.VendorRepository;
import com.ari.vendormanagement.service.CategoryService;
import com.ari.vendormanagement.service.VendorBankService;
import com.ari.vendormanagement.service.VendorService;
import java.util.List;
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
public class VendorServiceImpl implements VendorService {
  private final VendorRepository repository;
  private final CategoryService categoryService;
  @Setter(onMethod_ = @Autowired, onParam_ = @Lazy)
  private VendorBankService vendorBankService;

  @Override
  public VendorResponse findById(String id) {
    return mappingToResponse(getById(id));
  }

  @Override
  public Responses<List<VendorResponse>> findAll(PagingRequest pagingRequest) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
    Page<Vendor> vendors = repository.findAll(pageable);
    List<Vendor> listOfVendor = vendors.getContent();
    List<VendorResponse> content = listOfVendor.stream().map(item -> mappingToResponse(item)).toList();
    Responses<List<VendorResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(vendors.getNumber()).pageSize(
            vendors.getSize()).totalElements(vendors.getTotalElements()).totalPages(vendors.getTotalPages())
        .last(vendors.isLast()).build());
    return response;
  }

  @Override
  public void edit(UpdateVendorRequest updateVendorRequest) {
    Vendor vendor = new Vendor();
    BeanUtils.copyProperties(updateVendorRequest, vendor);
    Category category = categoryService.getById(updateVendorRequest.getCategoryId());
    vendor.setCategory(category);
    repository.saveAndFlush(vendor);
  }

  @Override
  public void deleteById(String id) {
    validateIsVendorUsed(id);
    repository.delete(getById(id));
  }

  @Override
  public void add(CreateVendorRequest createVendorRequest) {
    validateBk(createVendorRequest.getCode(), createVendorRequest.getEmail());
    Vendor vendor = new Vendor();
    BeanUtils.copyProperties(createVendorRequest, vendor);
    Category category = categoryService.getById(createVendorRequest.getCategoryId());
    vendor.setCategory(category);
    repository.saveAndFlush(vendor);
  }

  @Override
  public Vendor getById(String id) {
    return repository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "vendor is not exist")
    );
  }

  @Override
  public boolean existsById(String id) {
    return repository.existsById(id);
  }

  void validateBk(String code, String email){
    if (repository.existsByCode(code)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "code already exist.");
    }
    if (repository.existsByEmail(email)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "email already exist.");
    }
  }

  private VendorResponse mappingToResponse(Vendor vendor){
    VendorResponse response = new VendorResponse();
    BeanUtils.copyProperties(vendor, response);
    response.setCategoryName(vendor.getCategory().getName());
    response.setCategoryId(vendor.getCategory().getId());
    return response;
  }

  private void validateIsVendorUsed(String vendorId){
    if (vendorBankService.existsByVendorId(vendorId)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "vendor is used in vendor bank account.");
    }
  }
}
