package com.ari.vendormanagement.service.impl;

import com.ari.vendormanagement.helper.CustomResponseException;
import com.ari.vendormanagement.model.request.CreateCategoryRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateCategoryRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.CategoryResponse;
import com.ari.vendormanagement.model.response.PageResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.persistence.entity.Bank;
import com.ari.vendormanagement.persistence.entity.Category;
import com.ari.vendormanagement.persistence.repository.CategoryRepository;
import com.ari.vendormanagement.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository repository;

  @Override
  public CategoryResponse findById(String id) {
    return mappingToResponse(getById(id));
  }

  @Override
  public Responses<List<CategoryResponse>> findAll(PagingRequest pagingRequest) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
    Page<Category> categories = repository.findAll(pageable);
    List<Category> listOfCategory = categories.getContent();
    List<CategoryResponse> content = listOfCategory.stream().map(item -> mappingToResponse(item)).toList();
    Responses<List<CategoryResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(categories.getNumber()).pageSize(
            categories.getSize()).totalElements(categories.getTotalElements()).totalPages(categories.getTotalPages())
        .last(categories.isLast()).build());
    return response;
  }

  @Override
  public void edit(UpdateCategoryRequest updateCategoryRequest) {
    Category category = getById(updateCategoryRequest.getId());
    BeanUtils.copyProperties(updateCategoryRequest, category);
    repository.saveAndFlush(category);
  }

  @Override
  public void deleteById(String id) {
    repository.delete(getById(id));
  }

  @Override
  public void add(CreateCategoryRequest createCategoryRequest) {
    validateBK(createCategoryRequest.getCode());
    Category category = new Category();
    BeanUtils.copyProperties(createCategoryRequest, category);
    repository.saveAndFlush(category);
  }

  @Override
  public Category getById(String id) {
    return repository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "category is not exist")
    );
  }

  @Override
  public boolean existsById(String id) {
    return existsById(id);
  }

  private void validateBK(String code){
    if (repository.existsByCode(code)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "category code already exist.");
    }
  }

  private CategoryResponse mappingToResponse(Category category){
    CategoryResponse response = new CategoryResponse();
    BeanUtils.copyProperties(category, response);
    return response;
  }
}
