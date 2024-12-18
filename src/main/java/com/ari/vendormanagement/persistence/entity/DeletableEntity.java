package com.ari.vendormanagement.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class DeletableEntity extends AuditableEntity{
  @Column(name = "deleted_at")
  private ZonedDateTime deletedAt;
}
