package com.bankmisr.automaticirrigationsystem.model.payload;

import com.bankmisr.automaticirrigationsystem.model.dtos.SlotDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotResponse
{
	private Long totalItems;
	private Integer totalPages;
	private List<SlotDTO> slots;
}