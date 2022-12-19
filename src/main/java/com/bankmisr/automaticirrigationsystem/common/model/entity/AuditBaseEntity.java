package com.bankmisr.automaticirrigationsystem.common.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditBaseEntity extends BaseEntity {

    @ApiModelProperty(hidden = true)
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @ApiModelProperty(hidden = true)
    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ApiModelProperty(hidden = true)
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @ApiModelProperty(hidden = true)
    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

}

