package com.bankmisr.automaticirrigationsystem.repository;

import com.bankmisr.automaticirrigationsystem.common.repository.core.BaseRepository;
import com.bankmisr.automaticirrigationsystem.model.entities.Slot;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends BaseRepository<Slot>, JpaSpecificationExecutor<Slot> {

}
