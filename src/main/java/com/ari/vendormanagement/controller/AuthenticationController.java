package com.ari.vendormanagement.controller;

import com.ari.vendormanagement.authentication.service.AuthenticationService;
import com.ari.vendormanagement.model.request.LoginRequest;
import com.ari.vendormanagement.model.request.RegisterUserRequest;
import com.ari.vendormanagement.model.response.JwtAuthenticationResponse;
import com.ari.vendormanagement.model.response.TransactionResponse;
import com.ari.vendormanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class AuthenticationController {
  private final UserService userService;
  private final AuthenticationService authenticationService;


  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> register(
      @RequestBody @Valid RegisterUserRequest registerUserRequest) {
    userService.registerUser(registerUserRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("user successfully register.")
        .build());
  }


  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authenticationService.login(request));

  }

}
