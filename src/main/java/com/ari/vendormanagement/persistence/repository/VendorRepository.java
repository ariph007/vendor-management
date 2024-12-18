package com.ari.vendormanagement.persistence.repository;

import com.ari.vendormanagement.persistence.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, String>, JpaSpecificationExecutor<Vendor> {
  boolean existsByCode(String code);
  boolean existsByEmail(String email);
}
