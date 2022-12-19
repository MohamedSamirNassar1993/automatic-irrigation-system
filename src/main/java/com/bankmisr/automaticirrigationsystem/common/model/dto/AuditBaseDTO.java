package com.bankmisr.automaticirrigationsystem.common.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AuditBaseDTO
{

	@ApiModelProperty(hidden = true)
	private LocalDateTime createdDate;
	@ApiModelProperty(hidden = true)
	private LocalDateTime modifiedDate;
	@ApiModelProperty(hidden = true)
	private String createdBy;
	@ApiModelProperty(hidden = true)
	private String modifiedBy;
}

