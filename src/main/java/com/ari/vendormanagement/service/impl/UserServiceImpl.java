package com.ari.vendormanagement.service.impl;

import com.ari.vendormanagement.authentication.model.UserPrinciple;
import com.ari.vendormanagement.helper.CustomResponseException;
import com.ari.vendormanagement.model.request.PagingRequest;
import com.ari.vendormanagement.model.request.RegisterUserRequest;
import com.ari.vendormanagement.model.request.UpdateUserRequest;
import com.ari.vendormanagement.model.response.PageResponse;
import com.ari.vendormanagement.model.response.Responses;
import com.ari.vendormanagement.model.response.UserResponse;
import com.ari.vendormanagement.persistence.entity.Role;
import com.ari.vendormanagement.persistence.entity.User;
import com.ari.vendormanagement.persistence.repository.UserRepository;
import com.ari.vendormanagement.service.RoleService;
import com.ari.vendormanagement.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);


  @Override
  public void registerUser(RegisterUserRequest request) {
    validateEmail(request.getEmail());
    validatePassword(request.getPassword());
    validateUsername(request.getUsername());
    User user = new User();
    Role role = roleService.getById(request.getRoleId());
    user.setRole(role);
    BeanUtils.copyProperties(request, user);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.saveAndFlush(user);
  }

  @Override
  public UserResponse findById(String id) {
    return mappingToResponse(getById(id));
  }

  @Override
  public Responses<List<UserResponse>> findAll(PagingRequest pagingRequest) {
    Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize());
    Page<User> users = userRepository.findAll(pageable);
    List<User> listOfUser = users.getContent();
    List<UserResponse> content = listOfUser.stream().map(item -> mappingToResponse(item)).toList();
    Responses<List<UserResponse>> response = new Responses<>();
    response.setData(content);
    response.setPageResponse(PageResponse.builder().pageNo(users.getNumber()).pageSize(
            users.getSize()).totalElements(users.getTotalElements()).totalPages(users.getTotalPages())
        .last(users.isLast()).build());
    return response;
  }

  @Override
  public void edit(UpdateUserRequest updateUserRequest) {
    User user = getById(updateUserRequest.getId());
    BeanUtils.copyProperties(updateUserRequest, user);
    userRepository.saveAndFlush(user);
  }

  @Override
  public void deleteById(String id) {
    userRepository.delete(getById(id));
  }

  @Override
  @Cacheable(value="userList", key="#user")
  public User getById(String id) {
    return userRepository.findById(id).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "user is not exist")
    );
  }


  @CacheEvict(value="userList", allEntries = true)
  @Scheduled(fixedDelayString = "${caching.spring.userListTTL}", initialDelay = 50000)
  public void deleteUserList() {
    LOG.info("Evict User List");
  }

  @Override
  @Cacheable(value="userList", key="#user")
  public User getByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(
        () -> new CustomResponseException(HttpStatus.BAD_REQUEST, "user is not exist")
    );
  }

  @Override
  public boolean existsById(String id) {
    return userRepository.existsById(id);
  }

  @Override
  public Optional<User> getUserByUsernameAndPassword(String username, String password) {
    return userRepository.findByUsername(username)
        .or(() -> userRepository.findByEmail(username));
  }

  @Override
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exist")
        );

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
        return UserPrinciple.builder().user(user).role(user.getRole()).authorities(authorities)
            .build();
      }
    };
  }

  @Override
  public boolean existByRoleId(String roleId) {
    return !userRepository.findByRoleId(roleId).isEmpty();
  }


  private void validateEmail(String email){
    Pattern validEmail =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    if(!validEmail.matcher(email).matches()){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "invalid email format.");
    }
    if(userRepository.existsByEmail(email)){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST, "email is already registered");
    }
  }

  private void validateUsername(String username){
    if (username.length() < 5) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "username must be at least 5 characters long.");
    }
    if (!username.matches("^[a-zA-Z0-9]*$")) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "username can only contain alphanumeric characters.");
    }
    if(userRepository.existsByUsername(username)){
      throw new CustomResponseException(HttpStatus.BAD_REQUEST, "username is already taken");
    }
  }

  private void validatePassword(String password){
    if (password.length() < 8) {
      throw new CustomResponseException(HttpStatus.BAD_REQUEST,
          "password must be at least 8 characters long.");
    }
    if (!password.matches(".*[a-z].*")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "password must contain at least one lowercase letter.");
    }
    if (!password.matches(".*[A-Z].*")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "password must contain at least one uppercase letter.");
    }
  }

  private UserResponse mappingToResponse(User user){
    UserResponse response = new UserResponse();
    BeanUtils.copyProperties(user, response);
    return response;
  }



}
