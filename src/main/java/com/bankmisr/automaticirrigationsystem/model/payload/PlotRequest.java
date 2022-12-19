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
public class PlotRequest extends AuditBaseDTO {

    @NotEmpty(message = Constants.ErrorKeys.EMPTY_PLOT_AR)
    private String nameAr;
    @NotEmpty(message = Constants.ErrorKeys.EMPTY_PLOT_EN)
    private String nameEn;
    private String searchableTextAr;
    private String searchableTextEn;
    private SensorRequest sensor;

}