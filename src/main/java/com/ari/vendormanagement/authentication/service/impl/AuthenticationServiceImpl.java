package com.ari.vendormanagement.authentication.service.impl;

import com.ari.vendormanagement.authentication.service.AuthenticationService;
import com.ari.vendormanagement.authentication.service.JwtService;
import com.ari.vendormanagement.model.request.LoginRequest;
import com.ari.vendormanagement.model.response.JwtAuthenticationResponse;
import com.ari.vendormanagement.persistence.entity.User;
import com.ari.vendormanagement.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  @Transactional
  public JwtAuthenticationResponse login(LoginRequest loginRequest) {
    Optional<User> userOpt = userService
        .getUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
    if (userOpt.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong username/password!");
    }
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(), loginRequest.getPassword()));
    var userPrinciple =
        userService.userDetailsService().loadUserByUsername(loginRequest.getUsername());
    String token = jwtService.generateToken(userPrinciple);
    return JwtAuthenticationResponse.builder().token(token).build();
  }
}
