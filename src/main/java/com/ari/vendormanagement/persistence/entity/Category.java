package com.ari.vendormanagement.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "categories",
    uniqueConstraints = {@UniqueConstraint(name = "banks_un", columnNames = {"transfer_code, code"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE categories SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class Category extends DeletableEntity{
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "code" , nullable = false)
  private String code;

  @Column(name = "description")
  private String description;
}
