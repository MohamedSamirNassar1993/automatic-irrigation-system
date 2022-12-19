package com.bankmisr.automaticirrigationsystem.repository;

import com.bankmisr.automaticirrigationsystem.common.config.core.BaseRepository;
import com.bankmisr.automaticirrigationsystem.model.entities.Sensor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends BaseRepository<Sensor>, JpaSpecificationExecutor<Sensor> {

    @Query("SELECT t FROM sensor t WHERE t.name_en = ?")
    Optional<Sensor> findByNameEn(String nameEn);
}
