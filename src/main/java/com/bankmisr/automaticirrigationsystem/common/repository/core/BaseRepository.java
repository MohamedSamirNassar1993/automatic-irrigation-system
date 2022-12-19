package com.bankmisr.automaticirrigationsystem.common.repository.core;

import com.bankmisr.automaticirrigationsystem.common.model.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long>
        , JpaSpecificationExecutor<T> {
    List<T> findAll(Specification<T> specification);

    Page<T> findAll(Specification<T> specification, Pageable pageable);
}
