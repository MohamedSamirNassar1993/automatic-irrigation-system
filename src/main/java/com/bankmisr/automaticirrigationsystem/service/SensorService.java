package com.bankmisr.automaticirrigationsystem.service;

import com.bankmisr.automaticirrigationsystem.common.exception.RecordNotFoundException;
import com.bankmisr.automaticirrigationsystem.graphql.filter.SensorFilter;
import com.bankmisr.automaticirrigationsystem.mapper.PlotMapper;
import com.bankmisr.automaticirrigationsystem.mapper.SensorMapper;
import com.bankmisr.automaticirrigationsystem.model.dtos.SensorDTO;
import com.bankmisr.automaticirrigationsystem.model.entities.Plot;
import com.bankmisr.automaticirrigationsystem.model.entities.Sensor;
import com.bankmisr.automaticirrigationsystem.model.enums.Language;
import com.bankmisr.automaticirrigationsystem.model.payload.SensorRequest;
import com.bankmisr.automaticirrigationsystem.model.payload.SensorResponse;
import com.bankmisr.automaticirrigationsystem.model.specification.SensorSpecification;
import com.bankmisr.automaticirrigationsystem.repository.PlotRepository;
import com.bankmisr.automaticirrigationsystem.repository.SensorRepository;
import com.bankmisr.automaticirrigationsystem.utils.ObjectChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SensorService {

    private final PlotMapper plotMapper;
    private final SensorMapper sensorMapper;
    private final PlotRepository plotRepository;
    private final SensorRepository sensorRepository;

    public SensorDTO createSensor(SensorRequest sensorRequest, String email) {
        Sensor sensor = sensorMapper.toEntity(sensorRequest);
        sensor.setCreatedBy(email);
        sensor.setModifiedBy(email);
        createPlotIfNotExists(sensorRequest, sensor);
        Sensor savedSensor = sensorRepository.save(sensor);
        return sensorMapper.toDTO(savedSensor);
    }

    private void createPlotIfNotExists(SensorRequest sensorRequest, Sensor sensor) {
        if (ObjectChecker.isEmptyOrNull(sensorRequest.getPlot())) return;
        Optional<Plot> foundPlot = plotRepository.findByNameEn(sensorRequest.getPlot().getNameEn());
        if (foundPlot.isPresent()) {
            Plot plot = foundPlot.get();
            updatePlotData(sensorRequest, plot);
            sensor.setPlot(plot);
        } else {
            Plot newPlot = plotRepository.save(plotMapper.toEntity(sensorRequest.getPlot()));
            sensor.setPlot(newPlot);
        }
    }

    private void updatePlotData(SensorRequest sensorRequest, Plot plot) {
        if (ObjectChecker.isNotEmptyOrNull(sensorRequest.getPlot())) {
            plot.setNameAr(sensorRequest.getPlot().getNameAr());
            plot.setNameEn(sensorRequest.getPlot().getNameEn());
        }
    }

    public SensorDTO updateSensor(SensorRequest sensorRequest, Long id, String email) {

        Sensor updatedSensor = sensorRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        createPlotIfNotExists(sensorRequest, updatedSensor);
        updatedSensorData(sensorRequest, updatedSensor,email);
        sensorRepository.save(updatedSensor);
        return sensorMapper.toDTO(updatedSensor);
    }

    private void updatedSensorData(SensorRequest sensorRequest, Sensor updatedSensor,String email) {
        updatedSensor.setNameAr(sensorRequest.getNameAr());
        updatedSensor.setNameEn(sensorRequest.getNameEn());
        updatedSensor.setSearchableTextAr(sensorRequest.getSearchableTextAr());
        updatedSensor.setSearchableTextEn(sensorRequest.getSearchableTextEn());
        updatedSensor.setUrl(sensorRequest.getUrl());
        updatedSensor.setModifiedBy(email);
    }

    public SensorDTO deleteSensor(Long id, String email) {

        Sensor sensor = sensorRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        sensorRepository.deleteById(id);
        return sensorMapper.toDTO(sensor);
    }

    public SensorResponse fetchSensors(SensorFilter filter, String email) {

        Specification<Sensor> specification = buildSpecifications(filter);
        PageRequest pageRequest = buildPagination(filter);
        Page<Sensor> page = executeSpecifications(specification, filter, pageRequest);
        if (ObjectChecker.isEmptyOrNull(page)) return new SensorResponse();
        List<Sensor> sensorList = page.getContent();
        List<SensorDTO> sensorDTOList =
                sensorList.stream().map(sensorMapper::toDTO).map(sensorDTO -> updateDataForSelectedLanguage(sensorDTO, filter.getLanguage(), email)).collect(Collectors.toList());
        SensorResponse sensorResponse = fillSensorResponse(page, sensorDTOList);
        return sensorResponse;
    }

    private SensorResponse fillSensorResponse(Page<Sensor> page, List<SensorDTO> sensorDTOList) {

        SensorResponse.SensorResponseBuilder sensorResponseBuilder = SensorResponse.builder();
        sensorResponseBuilder.sensors(sensorDTOList);
        sensorResponseBuilder.totalItems(page.getTotalElements());
        sensorResponseBuilder.totalPages(page.getTotalPages());
        return sensorResponseBuilder.build();
    }

    private SensorDTO updateDataForSelectedLanguage(SensorDTO sensorDTO, String language, String email) {

        String En = Language.EN.name().toLowerCase();
        sensorDTO.setName(ObjectChecker.areEqual(En, language) ? sensorDTO.getNameEn() : sensorDTO.getNameAr());

        if (ObjectChecker.isEmptyOrNull(sensorDTO.getPlot())) return sensorDTO;
        sensorDTO.getPlot().setName(ObjectChecker.areEqual(En, language) ? sensorDTO.getPlot().getNameEn() : sensorDTO.getPlot().getNameAr());
        return sensorDTO;
    }

    private Page<Sensor> executeSpecifications(Specification<Sensor> specification, SensorFilter filter, PageRequest pageRequest) {

        Page<Sensor> page;
        if (ObjectChecker.isEmptyOrNull(filter)) page = sensorRepository.findAll(pageRequest);
        else page = sensorRepository.findAll(specification, pageRequest);
        return page;
    }

    private PageRequest buildPagination(SensorFilter filter) {

        return PageRequest.of(filter.getOffset(), filter.getLimit(), Sort.by(Sort.Direction.valueOf(filter.getSortDirection()), filter.getSortBy()));
    }

    private Specification<Sensor> buildSpecifications(SensorFilter filter) {

        return Specification.where(SensorSpecification.id(filter.getId())).and(SensorSpecification.searchKeyword(filter.getKeyword(), filter.getLanguage()));
    }
}