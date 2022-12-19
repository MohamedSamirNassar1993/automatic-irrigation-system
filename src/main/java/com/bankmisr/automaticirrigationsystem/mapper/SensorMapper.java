package com.bankmisr.automaticirrigationsystem.mapper;

import com.bankmisr.automaticirrigationsystem.common.mapper.BaseMapper;
import com.bankmisr.automaticirrigationsystem.model.dtos.SensorDTO;
import com.bankmisr.automaticirrigationsystem.model.entities.Sensor;
import com.bankmisr.automaticirrigationsystem.model.payload.SensorRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorMapper extends BaseMapper<Object, SensorDTO, Sensor> {

    Sensor toEntity(SensorRequest sensorRequest);
}
