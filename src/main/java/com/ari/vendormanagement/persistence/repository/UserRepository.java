package com.ari.vendormanagement.persistence.repository;

import com.ari.vendormanagement.persistence.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
  boolean existsByEmail(String email);
  boolean existsByUsername(String email);
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);

  List<User> findByRoleId(String roleId);
}
