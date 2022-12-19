package com.bankmisr.automaticirrigationsystem.model.payload;

import com.bankmisr.automaticirrigationsystem.model.dtos.PlotDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlotResponse
{
	private Long totalItems;
	private Integer totalPages;
	private List<PlotDTO> plots;
}