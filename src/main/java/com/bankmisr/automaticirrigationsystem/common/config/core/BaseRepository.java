package com.bankmisr.automaticirrigationsystem.common.config.core;

import com.bankmisr.automaticirrigationsystem.common.model.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
