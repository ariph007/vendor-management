package com.ari.vendormanagement.persistence.repository;

import com.ari.vendormanagement.persistence.entity.VendorBank;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorBankRepository extends JpaRepository<VendorBank, String>, JpaSpecificationExecutor<VendorBank> {

  boolean existsByAccountNo(String accountNo);
  List<VendorBank> findByVendorId(String vendorId);
  List<VendorBank> findByBankId(String bankId);
}
