package com.bankmisr.automaticirrigationsystem.mapper;

import com.bankmisr.automaticirrigationsystem.common.mapper.BaseMapper;
import com.bankmisr.automaticirrigationsystem.model.dtos.PlotDTO;
import com.bankmisr.automaticirrigationsystem.model.entities.Plot;
import com.bankmisr.automaticirrigationsystem.model.payload.PlotRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlotMapper extends BaseMapper<Object, PlotDTO, Plot> {

    Plot toEntity(PlotRequest plotRequest);
}
