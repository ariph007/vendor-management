package com.ari.vendormanagement.service;

import com.ari.vendormanagement.model.request.CreateCategoryRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateCategoryRequest;
import com.ari.vendormanagement.model.response.CategoryResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.persistence.entity.Category;
import java.util.List;

public interface CategoryService {
  CategoryResponse findById(String id);
  Responses<List<CategoryResponse>> findAll(PagingRequest pagingRequest);
  void edit(UpdateCategoryRequest updateCategoryRequest);
  void deleteById(String id);
  void add(CreateCategoryRequest createCategoryRequest);
  Category getById(String id);
  boolean existsById(String id);
}
