package com.ari.vendormanagement.controller;

import com.ari.vendormanagement.model.request.CreateBankRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.RegisterUserRequest;
import com.ari.vendormanagement.model.request.UpdateBankRequest;
import com.ari.vendormanagement.model.request.UpdateUserRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.TransactionResponse;
import com.ari.vendormanagement.model.response.UserResponse;
import com.ari.vendormanagement.service.BankService;
import com.ari.vendormanagement.service.UserService;
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
public class UserController {
  private final UserService userService;

  @GetMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Responses<List<UserResponse>>> findAll(PagingRequest pagingRequest) {
    return ResponseEntity.ok(userService.findAll(pagingRequest));
  }

  @PostMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> register(
      @RequestBody @Valid RegisterUserRequest registerUserRequest) {
    userService.registerUser(registerUserRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("user successfully register.")
        .build());
  }


  @PutMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateUserRequest updateUserRequest) {
    userService.edit(updateUserRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("user successfully updated.")
        .build());
  }

  @DeleteMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable String id) {
    userService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("user successfully deleted.")
        .build());
  }

}
