package com.bankmisr.automaticirrigationsystem.model.payload;

import com.bankmisr.automaticirrigationsystem.common.model.dto.AuditBaseDTO;
import com.bankmisr.automaticirrigationsystem.common.util.Constants;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequest extends AuditBaseDTO {

    @NotEmpty(message = Constants.ErrorKeys.EMPTY_SENSOR_AR)
    private String nameAr;
    @NotEmpty(message = Constants.ErrorKeys.EMPTY_SENSOR_EN)
    private String nameEn;
    private String url;
    private String searchableTextAr;
    private String searchableTextEn;
    private PlotRequest plot;
}