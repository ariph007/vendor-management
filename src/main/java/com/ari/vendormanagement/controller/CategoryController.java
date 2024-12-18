package com.ari.vendormanagement.controller;

import com.ari.vendormanagement.model.request.CreateCategoryRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateCategoryRequest;
import com.ari.vendormanagement.model.response.CategoryResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.TransactionResponse;
import com.ari.vendormanagement.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping(value = "categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CategoryResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(categoryService.findById(id));
  }

  @GetMapping(value = "categories", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Responses<List<CategoryResponse>>> findAll(PagingRequest pagingRequest) {
    return ResponseEntity.ok(categoryService.findAll(pagingRequest));
  }

  @PostMapping(value = "categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> add(
      @RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
    categoryService.add(createCategoryRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("category successfully added.")
        .build());
  }


  @PutMapping(value = "categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest) {
    categoryService.edit(updateCategoryRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("category successfully updated.")
        .build());
  }

  @DeleteMapping(value = "categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable String id) {
    categoryService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("category successfully deleted.")
        .build());
  }

}
