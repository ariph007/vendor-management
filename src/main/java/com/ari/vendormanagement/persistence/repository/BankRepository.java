package com.ari.vendormanagement.persistence.repository;

import com.ari.vendormanagement.persistence.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, String>, JpaSpecificationExecutor<Bank> {
  boolean existsByTransferCode(String transferCode);
  boolean existsByCode(String code);
}
