package com.ari.vendormanagement.controller;

import com.ari.vendormanagement.model.request.CreateBankRequest;
import com.ari.vendormanagement.model.request.CreateVendorRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateBankRequest;
import com.ari.vendormanagement.model.request.UpdateVendorRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.TransactionResponse;
import com.ari.vendormanagement.model.response.VendorResponse;
import com.ari.vendormanagement.service.BankService;
import com.ari.vendormanagement.service.VendorService;
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
public class VendorController {
  private final VendorService vendorService;

  @GetMapping(value = "vendors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VendorResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(vendorService.findById(id));
  }

  @GetMapping(value = "vendors", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Responses<List<VendorResponse>>> findAll(PagingRequest pagingRequest) {
    return ResponseEntity.ok(vendorService.findAll(pagingRequest));
  }

  @PostMapping(value = "vendors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> add(
      @RequestBody @Valid CreateVendorRequest createVendorRequest) {
    vendorService.add(createVendorRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("vendor successfully added.")
        .build());
  }

  @PutMapping(value = "vendors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateVendorRequest updateVendorRequest) {
    vendorService.edit(updateVendorRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("vendor successfully updated.")
        .build());
  }

  @DeleteMapping(value = "vendors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable String id) {
    vendorService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("vendor successfully deleted.")
        .build());
  }

}
