package com.bankmisr.automaticirrigationsystem.endpoints;

import com.bankmisr.automaticirrigationsystem.common.model.payload.ApiResponse;
import com.bankmisr.automaticirrigationsystem.model.dtos.PlotDTO;
import com.bankmisr.automaticirrigationsystem.model.payload.PlotRequest;
import com.bankmisr.automaticirrigationsystem.service.PlotService;
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
@RequestMapping("/plot")
@Api(description = " ", tags = "PLOT", value = " ")
public class PlotController {

    private final PlotService plotService;

    @ApiOperation(value = "PLOT - Add PLOT", tags = "PLOT")
    @PostMapping
    public ApiResponse<PlotDTO> createPlot(@Valid @RequestBody PlotRequest plotRequest, @NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        PlotDTO savedEntity = plotService.createPlot(plotRequest,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

    @ApiOperation(value = "PLOT - Update PLOT", tags = "PLOT")
    @PutMapping("/{id}")
    public ApiResponse<PlotDTO> updatePlot(@Valid @RequestBody PlotRequest plotRequest, @PathVariable Long id,@NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        PlotDTO savedEntity = plotService.updatePlot(plotRequest, id,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

    @ApiOperation(value = "PLOT - Delete PLOT", tags = "PLOT")
    @DeleteMapping("/{id}")
    public ApiResponse<PlotDTO> deletePlot(@PathVariable Long id,@NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        PlotDTO savedEntity = plotService.deletePlot(id,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

}
