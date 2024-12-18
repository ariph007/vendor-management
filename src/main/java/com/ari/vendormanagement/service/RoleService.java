package com.ari.vendormanagement.service;

import com.ari.vendormanagement.model.request.CreateRoleRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateRoleRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.RoleResponse;
import com.ari.vendormanagement.persistence.entity.Role;
import java.util.List;

public interface RoleService {
  RoleResponse findById(String id);
  Responses<List<RoleResponse>> findAll(PagingRequest pagingRequest);
  void edit(UpdateRoleRequest updateRoleRequest);
  void deleteById(String id);
  void add(CreateRoleRequest createRoleRequest);
  Role getById(String id);
  boolean existsById(String id);
}
