package com.ari.vendormanagement.service.impl;

import com.ari.vendormanagement.helper.CustomResponseException;
import com.ari.vendormanagement.model.request.CreateBankRequest;
import com.ari.vendormanagement.model.request.CreateRoleRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateBankRequest;
import com.ari.vendormanagement.model.request.UpdateRoleRequest;
import com.ari.vendormanagement.model.response.BankResponse;
import com.ari.vendormanagement.model.response.PageResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.RoleResponse;
import com.ari.vendormanagement.persistence.entity.Bank;
import com.ari.vendormanagement.persistence.entity.Role;
import com.ari.vendormanagement.persistence.repository.RoleRepository;
import com.ari.vendormanagement.service.RoleService;
import com.ari.vendormanagement.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository repository;

  @Setter(onMethod_ = {@Autowired}, onParam_ = {@Lazy})
  private UserService userService;

  @Override
  public RoleResponse findById(String id) {
    return mappingToResponse(getById(id));
  }

  @Override
  public Responses<List<RoleResponse>> findAll(PagingRequest pagingRequest) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
    Page<Role> roles = repository.findAll(pageable);
    List<Role> listOfRole = roles.getContent();
    List<RoleResponse> content = listOfRole.stream().map(item -> mappingToResponse(item)).toList();
    Responses<List<RoleResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(roles.getNumber()).pageSize(
            roles.getSize()).totalElements(roles.getTotalElements()).totalPages(roles.getTotalPages())
        .last(roles.isLast()).build());
    return response;
  }

  @Override
  public void edit(UpdateRoleRequest updateRoleRequest) {
    Role role = getById(updateRoleRequest.getId());
    BeanUtils.copyProperties(updateRoleRequest, role);
    repository.saveAndFlush(role);
  }

  @Override
  public void deleteById(String id) {
    validateRoleIsUsed(id);
    repository.delete(getById(id));
  }

  @Override
  public void add(CreateRoleRequest createRoleRequest) {
    Role role = new Role();
    BeanUtils.copyProperties(createRoleRequest, role);
    repository.saveAndFlush(role);
  }

  @Override
  public Role getById(String id) {
    return repository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "bank is not exist")
    );
  }

  @Override
  public boolean existsById(String id) {
    return repository.existsById(id);
  }



  private void validateBk(String code){
    if (repository.existsByCode(code)) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "role code already exist.");
    }
  }

  private void validateRoleIsUsed(String roleId){
    if(userService.existByRoleId(roleId)){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "role is used");
    }
  }

  private RoleResponse mappingToResponse(Role role){
    RoleResponse response = new RoleResponse();
    BeanUtils.copyProperties(role, response);
    return response;
  }
}
