package com.bankmisr.automaticirrigationsystem.repository;


import com.bankmisr.automaticirrigationsystem.common.config.core.BaseRepository;
import com.bankmisr.automaticirrigationsystem.model.entities.Plot;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlotRepository extends BaseRepository<Plot>, JpaSpecificationExecutor<Plot> {

    @Query("SELECT t FROM plot t WHERE t.name_en = ?")
    Optional<Plot> findByNameEn(String nameEn);
}
