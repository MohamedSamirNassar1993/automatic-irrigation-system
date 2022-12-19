package com.bankmisr.automaticirrigationsystem.endpoints;


import com.bankmisr.automaticirrigationsystem.common.model.payload.ApiResponse;
import com.bankmisr.automaticirrigationsystem.model.dtos.SensorDTO;
import com.bankmisr.automaticirrigationsystem.model.dtos.SlotDTO;
import com.bankmisr.automaticirrigationsystem.model.payload.SensorRequest;
import com.bankmisr.automaticirrigationsystem.model.payload.SlotRequest;
import com.bankmisr.automaticirrigationsystem.service.SlotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/slot")
@Api(description = " ", tags = "SLOT", value = " ")
public class SlotController {
    private final SlotService slotService;

    @ApiOperation(value = "Slot - Add Slot", tags = "Slot")
    @PostMapping
    public ApiResponse<SlotDTO> createSlot(@Valid @RequestBody SlotRequest slotRequest, @NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        SlotDTO savedEntity = slotService.createSlot(slotRequest,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

    @ApiOperation(value = "Slot - Update Slot", tags = "Slot")
    @PutMapping("/{id}")
    public ApiResponse<SlotDTO> updateSlot(@Valid @RequestBody SlotRequest slotRequest, @PathVariable Long id,@NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        SlotDTO savedEntity = slotService.updateSlot(slotRequest, id,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

    @ApiOperation(value = "Slot - Delete Slot", tags = "Slot")
    @DeleteMapping("/{id}")
    public ApiResponse<SlotDTO> deleteSlot(@PathVariable Long id,@NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        SlotDTO savedEntity = slotService.deleteSlot(id,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

}
