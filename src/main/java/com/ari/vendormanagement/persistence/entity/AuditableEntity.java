package com.ari.vendormanagement.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {
  @Id
  @Column(name = "id", nullable = false)
  @UuidGenerator
  private String id;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  private ZonedDateTime createdAt;

  @Column(name = "created_by", nullable = false, updatable = false)
  @CreatedBy
  private String createdBy;

  @Column(name = "updated_at", nullable = false)
  @LastModifiedDate
  private ZonedDateTime updatedAt;

  @Column(name = "updated_by", nullable = false)
  @LastModifiedBy
  private String updatedBy;

  @Column(name = "version")
  @Version
  private Long version;
}
