package com.bankmisr.automaticirrigationsystem.mapper;

import com.bankmisr.automaticirrigationsystem.common.mapper.BaseMapper;
import com.bankmisr.automaticirrigationsystem.model.dtos.SlotDTO;
import com.bankmisr.automaticirrigationsystem.model.entities.Slot;
import com.bankmisr.automaticirrigationsystem.model.payload.SlotRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SlotMapper extends BaseMapper<Object, SlotDTO, Slot> {

    Slot toEntity(SlotRequest slotRequest);
}
