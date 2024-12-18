package com.ari.vendormanagement.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "vendor_banks",
    uniqueConstraints = {@UniqueConstraint(name = "vendor_banks_un", columnNames = {"account_no", "bank_id", "vendor_id"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE vendor_banks SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class VendorBank extends DeletableEntity{
  @Column(name = "account_no", nullable = false)
  private String accountNo;

  @Column(name = "account_name", nullable = false)
  private String accountName;


  @ManyToOne()
  @JoinColumn(name = "bank_id", nullable = false)
  private Bank bank;

  @ManyToOne()
  @JoinColumn(name = "vendor_id", nullable = false)
  private Vendor vendor;
}
