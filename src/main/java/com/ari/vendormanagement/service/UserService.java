package com.ari.vendormanagement.service;

import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.RegisterUserRequest;
import com.ari.vendormanagement.model.request.UpdateUserRequest;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.UserResponse;
import com.ari.vendormanagement.persistence.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
  void registerUser(RegisterUserRequest request);
  UserResponse findById(String id);
  Responses<List<UserResponse>> findAll(PagingRequest pagingRequest);
  void edit(UpdateUserRequest updateUserRequest);
  void deleteById(String id);
  User getById(String id);
  boolean existsById(String id);
  Optional<User> getUserByUsernameAndPassword(String username, String password);
  UserDetailsService userDetailsService();
  boolean existByRoleId(String roleId);
  void deleteUserList();
  User getByUsername(String username);
}
