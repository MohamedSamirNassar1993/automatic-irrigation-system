package com.bankmisr.automaticirrigationsystem.model.dtos;

import com.bankmisr.automaticirrigationsystem.common.model.dto.AuditBaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@ApiModel
public class PlotDTO extends AuditBaseDTO {

    private Long id;
    private String name;
    private String nameAr;
    private String nameEn;
    private Set<SlotDTO> slots = new LinkedHashSet<>();
    private SensorDTO sensor;
}
