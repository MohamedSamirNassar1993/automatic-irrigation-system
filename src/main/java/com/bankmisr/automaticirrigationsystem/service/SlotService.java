package com.bankmisr.automaticirrigationsystem.service;


import com.bankmisr.automaticirrigationsystem.common.exception.RecordNotFoundException;
import com.bankmisr.automaticirrigationsystem.graphql.filter.SlotFilter;
import com.bankmisr.automaticirrigationsystem.mapper.PlotMapper;
import com.bankmisr.automaticirrigationsystem.mapper.SlotMapper;
import com.bankmisr.automaticirrigationsystem.model.dtos.SlotDTO;
import com.bankmisr.automaticirrigationsystem.model.entities.Plot;
import com.bankmisr.automaticirrigationsystem.model.entities.Slot;
import com.bankmisr.automaticirrigationsystem.model.enums.Language;
import com.bankmisr.automaticirrigationsystem.model.payload.SlotRequest;
import com.bankmisr.automaticirrigationsystem.model.payload.SlotResponse;
import com.bankmisr.automaticirrigationsystem.model.specification.SlotSpecification;
import com.bankmisr.automaticirrigationsystem.repository.PlotRepository;
import com.bankmisr.automaticirrigationsystem.repository.SlotRepository;
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
public class SlotService {

    private final PlotMapper plotMapper;
    private final SlotMapper slotMapper;
    private final PlotRepository plotRepository;
    private final SlotRepository slotRepository;

    public SlotDTO createSlot(SlotRequest slotRequest, String email) {
        Slot slot = slotMapper.toEntity(slotRequest);
        slot.setCreatedBy(email);
        slot.setModifiedBy(email);
        createPlotIfNotExists(slotRequest, slot);
        Slot savedSlot = slotRepository.save(slot);
        return slotMapper.toDTO(savedSlot);
    }

    private void createPlotIfNotExists(SlotRequest slotRequest, Slot slot) {
        if (ObjectChecker.isEmptyOrNull(slotRequest.getPlot())) return;
        Optional<Plot> foundPlot = plotRepository.findByNameEn(slotRequest.getPlot().getNameEn());
        if (foundPlot.isPresent()) {
            Plot plot = foundPlot.get();
            updatePlotData(slotRequest, plot);
            slot.setPlot(plot);
        } else {
            Plot newPlot = plotRepository.save(plotMapper.toEntity(slotRequest.getPlot()));
            slot.setPlot(newPlot);
        }
    }

    private void updatePlotData(SlotRequest slotRequest, Plot plot) {
        if (ObjectChecker.isNotEmptyOrNull(slotRequest.getPlot())) {
            plot.setNameAr(slotRequest.getPlot().getNameAr());
            plot.setNameEn(slotRequest.getPlot().getNameEn());
        }
    }

    public SlotDTO updateSlot(SlotRequest slotRequest, Long id, String email) {
        Slot updatedSlot = slotRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        createPlotIfNotExists(slotRequest, updatedSlot);
        updatedSlotData(slotRequest, updatedSlot,email);
        slotRepository.save(updatedSlot);
        return slotMapper.toDTO(updatedSlot);
    }

    private void updatedSlotData(SlotRequest slotRequest, Slot updatedSlot, String email) {
        updatedSlot.setNameAr(slotRequest.getNameAr());
        updatedSlot.setNameEn(slotRequest.getNameEn());
        updatedSlot.setSearchableTextAr(slotRequest.getSearchableTextAr());
        updatedSlot.setSearchableTextEn(slotRequest.getSearchableTextEn());
        updatedSlot.setStartTime(slotRequest.getStartTime());
        updatedSlot.setEndTime(slotRequest.getEndTime());
        updatedSlot.setStatus(slotRequest.getStatus());
        updatedSlot.setWaterRequired(slotRequest.getWaterRequired());
        updatedSlot.setModifiedBy(email);
    }

    public SlotDTO deleteSlot(Long id, String email) {
        Slot slot = slotRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        slotRepository.deleteById(id);
        return slotMapper.toDTO(slot);
    }

    public SlotResponse fetchSlots(SlotFilter filter, String email) {

        Specification<Slot> specification = buildSpecifications(filter);
        PageRequest pageRequest = buildPagination(filter);
        Page<Slot> page = executeSpecifications(specification, filter, pageRequest);
        if (ObjectChecker.isEmptyOrNull(page)) return new SlotResponse();
        List<Slot> slotList = page.getContent();
        List<SlotDTO> slotDTOList =
                slotList.stream().map(slotMapper::toDTO).map(slotDTO -> updateDataForSelectedLanguage(slotDTO, filter.getLanguage(), email)).collect(Collectors.toList());
        SlotResponse slotResponse = fillSlotResponse(page, slotDTOList);
        return slotResponse;
    }

    private SlotResponse fillSlotResponse(Page<Slot> page, List<SlotDTO> slotDTOList) {

        SlotResponse.SlotResponseBuilder slotResponseBuilder = SlotResponse.builder();
        slotResponseBuilder.slots(slotDTOList);
        slotResponseBuilder.totalItems(page.getTotalElements());
        slotResponseBuilder.totalPages(page.getTotalPages());
        return slotResponseBuilder.build();
    }

    private SlotDTO updateDataForSelectedLanguage(SlotDTO slotDTO, String language, String email) {

        String En = Language.EN.name().toLowerCase();
        slotDTO.setName(ObjectChecker.areEqual(En, language) ? slotDTO.getNameEn() : slotDTO.getNameAr());

        if (ObjectChecker.isEmptyOrNull(slotDTO.getPlot())) return slotDTO;
        slotDTO.getPlot().setName(ObjectChecker.areEqual(En, language) ? slotDTO.getPlot().getNameEn() : slotDTO.getPlot().getNameAr());
        return slotDTO;
    }

    private Page<Slot> executeSpecifications(Specification<Slot> specification, SlotFilter filter, PageRequest pageRequest) {

        Page<Slot> page;
        if (ObjectChecker.isEmptyOrNull(filter)) page = slotRepository.findAll(pageRequest);
        else page = slotRepository.findAll(specification, pageRequest);
        return page;
    }

    private PageRequest buildPagination(SlotFilter filter) {

        return PageRequest.of(filter.getOffset(), filter.getLimit(), Sort.by(Sort.Direction.valueOf(filter.getSortDirection()), filter.getSortBy()));
    }

    private Specification<Slot> buildSpecifications(SlotFilter filter) {

        return Specification.where(SlotSpecification.id(filter.getId())).and(SlotSpecification.searchKeyword(filter.getKeyword(), filter.getLanguage()));
    }
}
