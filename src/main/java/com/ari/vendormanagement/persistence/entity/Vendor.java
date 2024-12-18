package com.ari.vendormanagement.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "vendors",
    uniqueConstraints = {@UniqueConstraint(name = "banks_un", columnNames = {"transfer_code, code"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE vendors SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class Vendor extends DeletableEntity{
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "code" , nullable = false)
  private String code;

  @Column(name = "description")
  private String description;

  @Column(name = "address")
  private String address;

  @Column(name = "email" , nullable = false)
  private String email;

  @OneToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

}
