package com.ari.vendormanagement.controller;

import com.ari.vendormanagement.model.request.CreateVendorBankRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateVendorBankRequest;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.TransactionResponse;
import com.ari.vendormanagement.model.response.VendorBankResponse;
import com.ari.vendormanagement.service.VendorBankService;
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
public class VendorBankController {

  private final VendorBankService vendorBankService;

  @GetMapping(value = "vendor-banks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VendorBankResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(vendorBankService.findById(id));
  }

  @GetMapping(value = "vendor-banks", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Responses<List<VendorBankResponse>>> findAll(PagingRequest pagingRequest) {
    return ResponseEntity.ok(vendorBankService.findAll(pagingRequest));
  }

  @PostMapping(value = "vendor-banks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> add(
      @RequestBody @Valid CreateVendorBankRequest createVendorBankRequest) {
    vendorBankService.add(createVendorBankRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("vendor bank successfully added.")
        .build());
  }


  @PutMapping(value = "vendor-banks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateVendorBankRequest updateVendorBankRequest) {
    vendorBankService.edit(updateVendorBankRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("vendor bank successfully updated.")
        .build());
  }

  @DeleteMapping(value = "vendor-banks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable String id) {
    vendorBankService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("vendor bank successfully deleted.")
        .build());
  }

}
