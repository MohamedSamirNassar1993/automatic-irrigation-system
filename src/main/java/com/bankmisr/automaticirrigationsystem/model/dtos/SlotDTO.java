package com.bankmisr.automaticirrigationsystem.model.dtos;

import com.bankmisr.automaticirrigationsystem.common.model.dto.AuditBaseDTO;
import com.bankmisr.automaticirrigationsystem.model.enums.Status;
import io.swagger.annotations.ApiModel;
import lombok.*;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@ApiModel
public class SlotDTO extends AuditBaseDTO {

    private Long id;
    private String name;
    private String nameAr;
    private String nameEn;
    private Long waterRequired;
    private Instant startTime;
    private Instant endTime;
    private Status status;
    private PlotDTO plot;

}
