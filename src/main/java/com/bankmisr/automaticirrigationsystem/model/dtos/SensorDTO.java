package com.bankmisr.automaticirrigationsystem.model.dtos;

import com.bankmisr.automaticirrigationsystem.common.model.dto.AuditBaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@ApiModel
public class SensorDTO extends AuditBaseDTO {

    private Long id;
    private String name;
    private String nameAr;
    private String nameEn;
    private String url;
    private PlotDTO plot;
}
