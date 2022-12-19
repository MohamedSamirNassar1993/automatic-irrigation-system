package com.bankmisr.automaticirrigationsystem.service;

import com.bankmisr.automaticirrigationsystem.common.exception.RecordNotFoundException;
import com.bankmisr.automaticirrigationsystem.graphql.filter.PlotFilter;
import com.bankmisr.automaticirrigationsystem.mapper.PlotMapper;
import com.bankmisr.automaticirrigationsystem.mapper.SensorMapper;
import com.bankmisr.automaticirrigationsystem.model.dtos.PlotDTO;
import com.bankmisr.automaticirrigationsystem.model.dtos.SlotDTO;
import com.bankmisr.automaticirrigationsystem.model.entities.Plot;
import com.bankmisr.automaticirrigationsystem.model.entities.Sensor;
import com.bankmisr.automaticirrigationsystem.model.enums.Language;
import com.bankmisr.automaticirrigationsystem.model.payload.PlotRequest;
import com.bankmisr.automaticirrigationsystem.model.payload.PlotResponse;
import com.bankmisr.automaticirrigationsystem.model.specification.PlotSpecification;
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
public class PlotService {

    private final PlotMapper plotMapper;
    private final SensorMapper sensorMapper;
    private final PlotRepository plotRepository;
    private final SensorRepository sensorRepository;

    public PlotDTO createPlot(PlotRequest plotRequest, String email){

        Plot plot = plotMapper.toEntity(plotRequest);
        plot.setCreatedBy(email);
        plot.setModifiedBy(email);
        createSensorIfNotExists(plotRequest, plot);
        Plot savedPlot = plotRepository.save(plot);
        return plotMapper.toDTO(savedPlot);

    }

    private void createSensorIfNotExists(PlotRequest plotRequest, Plot plot) {

        if (ObjectChecker.isEmptyOrNull(plotRequest.getSensor())) return;
        Optional<Sensor> foundSensor = sensorRepository.findByNameEn(plotRequest.getSensor().getNameEn());
        if (foundSensor.isPresent()) {
            Sensor sensor = foundSensor.get();
            updateSensorData(plotRequest, sensor);
            plot.setSensor(sensor);
        } else {
            Sensor newSensor = sensorRepository.save(sensorMapper.toEntity(plotRequest.getSensor()));
            plot.setSensor(newSensor);
        }
    }

    private void updateSensorData(PlotRequest plotRequest, Sensor sensor) {
        if (ObjectChecker.isNotEmptyOrNull(plotRequest.getSensor())) {
            sensor.setNameAr(plotRequest.getSensor().getNameAr());
            sensor.setNameEn(plotRequest.getSensor().getNameEn());
        }
    }

    public PlotDTO updatePlot(PlotRequest plotRequest, Long id, String email) {

        Plot updatedPlot = plotRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        createSensorIfNotExists(plotRequest, updatedPlot);
        updatedPlotData(plotRequest, updatedPlot,email);
        plotRepository.save(updatedPlot);
        return plotMapper.toDTO(updatedPlot);
    }

    private void updatedPlotData(PlotRequest plotRequest, Plot updatedPlot, String email) {
        updatedPlot.setNameAr(plotRequest.getNameAr());
        updatedPlot.setNameEn(plotRequest.getNameEn());
        updatedPlot.setSearchableTextAr(plotRequest.getSearchableTextAr());
        updatedPlot.setSearchableTextEn(plotRequest.getSearchableTextEn());
        updatedPlot.setModifiedBy(email);
    }

    public PlotDTO deletePlot(Long id, String email) {

        Plot plot = plotRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        plotRepository.deleteById(id);
        return plotMapper.toDTO(plot);
    }

    public PlotResponse fetchPlots(PlotFilter filter, String email) {
        Specification<Plot> specification = buildSpecifications(filter);
        PageRequest pageRequest = buildPagination(filter);
        Page<Plot> page = executeSpecifications(specification, filter, pageRequest);
        if (ObjectChecker.isEmptyOrNull(page)) return new PlotResponse();
        List<Plot> plotList = page.getContent();
        List<PlotDTO> plotDTOList =
                plotList.stream().map(plotMapper::toDTO).map(plotDTO -> updateDataForSelectedLanguage(plotDTO, filter.getLanguage(), email)).collect(Collectors.toList());
        PlotResponse plotResponse = fillPlotResponse(page, plotDTOList);
        return plotResponse;
    }

    private PlotResponse fillPlotResponse(Page<Plot> page, List<PlotDTO> plotDTOList) {

        PlotResponse.PlotResponseBuilder plotResponseBuilder = PlotResponse.builder();
        plotResponseBuilder.plots(plotDTOList);
        plotResponseBuilder.totalItems(page.getTotalElements());
        plotResponseBuilder.totalPages(page.getTotalPages());
        return plotResponseBuilder.build();
    }

    private PlotDTO updateDataForSelectedLanguage(PlotDTO plotDTO, String language, String email) {

        String En = Language.EN.name().toLowerCase();
        plotDTO.setName(ObjectChecker.areEqual(En, language) ? plotDTO.getNameEn() : plotDTO.getNameAr());

        if (ObjectChecker.isNotEmptyOrNull(plotDTO.getSensor())&&ObjectChecker.isNotEmptyOrNull(plotDTO.getSlots())) {
            plotDTO.getSensor().setName(ObjectChecker.areEqual(En, language) ? plotDTO.getSensor().getNameEn() : plotDTO.getSensor().getNameAr());
            for(SlotDTO slotDTO : plotDTO.getSlots()){
                slotDTO.setName(ObjectChecker.areEqual(En, language) ? slotDTO.getNameEn() : slotDTO.getNameAr());
            }
        } else if (ObjectChecker.isNotEmptyOrNull(plotDTO.getSensor())||ObjectChecker.isNotEmptyOrNull(plotDTO.getSlots())) {
            if(ObjectChecker.isNotEmptyOrNull(plotDTO.getSensor()))
                plotDTO.getSensor().setName(ObjectChecker.areEqual(En, language) ? plotDTO.getSensor().getNameEn() : plotDTO.getSensor().getNameAr());

            if(ObjectChecker.isNotEmptyOrNull(plotDTO.getSlots())){
                for(SlotDTO slotDTO : plotDTO.getSlots()){
                    slotDTO.setName(ObjectChecker.areEqual(En, language) ? slotDTO.getNameEn() : slotDTO.getNameAr());
                }
            }
        }

        if (ObjectChecker.isEmptyOrNull(plotDTO.getSensor())) return plotDTO;

        if (ObjectChecker.isEmptyOrNull(plotDTO.getSlots())) return plotDTO;

        return plotDTO;
    }

    private Page<Plot> executeSpecifications(Specification<Plot> specification, PlotFilter filter, PageRequest pageRequest) {

        Page<Plot> page;
        if (ObjectChecker.isEmptyOrNull(filter)) page = plotRepository.findAll(pageRequest);
        else page = plotRepository.findAll(specification, pageRequest);
        return page;
    }

    private PageRequest buildPagination(PlotFilter filter) {

        return PageRequest.of(filter.getOffset(), filter.getLimit(), Sort.by(Sort.Direction.valueOf(filter.getSortDirection()), filter.getSortBy()));
    }

    private Specification<Plot> buildSpecifications(PlotFilter filter) {

        return Specification.where(PlotSpecification.id(filter.getId())).and(PlotSpecification.searchKeyword(filter.getKeyword(), filter.getLanguage()));
    }
}