package com.bankmisr.automaticirrigationsystem.model.payload;

import com.bankmisr.automaticirrigationsystem.common.model.dto.AuditBaseDTO;
import com.bankmisr.automaticirrigationsystem.common.util.Constants;

import com.bankmisr.automaticirrigationsystem.model.enums.Status;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class SlotRequest extends AuditBaseDTO {

    @NotEmpty(message = Constants.ErrorKeys.EMPTY_SLOT_AR)
    private String nameAr;
    @NotEmpty(message = Constants.ErrorKeys.EMPTY_SLOT_EN)
    private String nameEn;
    private Long waterRequired;
    private Instant startTime;
    private Instant endTime;
    private Status status = Status.INACTIVE;
    private String searchableTextAr;
    private String searchableTextEn;
    private PlotRequest plot;
}