package com.ari.vendormanagement.controller;

import com.ari.vendormanagement.model.request.CreateRoleRequest;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.UpdateRoleRequest;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.RoleResponse;
import com.ari.vendormanagement.model.response.TransactionResponse;
import com.ari.vendormanagement.service.RoleService;
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
public class RoleController {
  private final RoleService roleService;

  @GetMapping(value = "roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RoleResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(roleService.findById(id));
  }

  @GetMapping(value = "roles", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Responses<List<RoleResponse>>> findAll(PagingRequest pagingRequest) {
    return ResponseEntity.ok(roleService.findAll(pagingRequest));
  }

  @PostMapping(value = "roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> add(
      @RequestBody @Valid CreateRoleRequest createRoleRequest) {
    roleService.add(createRoleRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("role successfully added.")
        .build());
  }


  @PutMapping(value = "roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> edit(
      @Valid @RequestBody UpdateRoleRequest updateRoleRequest) {
    roleService.edit(updateRoleRequest);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("role successfully updated.")
        .build());
  }

  @DeleteMapping(value = "roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionResponse> delete(@PathVariable String id) {
    roleService.deleteById(id);
    return ResponseEntity.ok(TransactionResponse.builder()
        .code(HttpStatus.OK.value())
        .message("role successfully deleted.")
        .build());
  }

}
