package com.ari.vendormanagement.authentication.service;


import com.ari.vendormanagement.model.request.LoginRequest;
import com.ari.vendormanagement.model.response.JwtAuthenticationResponse;

public interface AuthenticationService {
  JwtAuthenticationResponse login(LoginRequest loginRequest);

}
