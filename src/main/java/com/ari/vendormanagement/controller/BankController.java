package com.ari.vendormanagement.controller;

import com.ari.vendormanagement.model.request.CreateBankRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateBankRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.TransactionResponse;
import com.ari.vendormanagement.service.BankService;
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
public class BankController {
  private final BankService bankService;

  @GetMapping(value = "banks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BankResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(bankService.findById(id));
  }

  @GetMapping(value = "banks", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Responses<List<BankResponse>>> findAll(PagingRequest pagingRequest) {
    return ResponseEntity.ok(bankService.findAll(pagingRequest));
  }

  @PostMapping(value = "banks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> add(
      @RequestBody @Valid CreateBankRequest createBankRequest) {
    bankService.add(createBankRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("bank successfully added.")
        .build());
  }


  @PutMapping(value = "banks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateBankRequest updateBankRequest) {
    bankService.edit(updateBankRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("bank successfully updated.")
        .build());
  }

  @DeleteMapping(value = "banks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable String id) {
    bankService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("bank successfully deleted.")
        .build());
  }

}
