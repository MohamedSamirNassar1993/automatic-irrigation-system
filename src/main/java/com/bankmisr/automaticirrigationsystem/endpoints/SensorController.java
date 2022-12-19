package com.bankmisr.automaticirrigationsystem.endpoints;

import com.bankmisr.automaticirrigationsystem.common.model.payload.ApiResponse;
import com.bankmisr.automaticirrigationsystem.model.dtos.SensorDTO;
import com.bankmisr.automaticirrigationsystem.model.payload.SensorRequest;
import com.bankmisr.automaticirrigationsystem.service.SensorService;
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
@RequestMapping("/sensor")
@Api(description = " ", tags = "SENSOR", value = " ")
public class SensorController {

    private final SensorService sensorService;

    @ApiOperation(value = "Sensor - Add Sensor", tags = "Sensor")
    @PostMapping
    public ApiResponse<SensorDTO> createSensor(@Valid @RequestBody SensorRequest sensorRequest, @NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        SensorDTO savedEntity = sensorService.createSensor(sensorRequest,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

    @ApiOperation(value = "Sensor - Update Sensor", tags = "Sensor")
    @PutMapping("/{id}")
    public ApiResponse<SensorDTO> updateSensor(@Valid @RequestBody SensorRequest sensorRequest, @PathVariable Long id,@NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        SensorDTO savedEntity = sensorService.updateSensor(sensorRequest, id,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

    @ApiOperation(value = "Sensor - Delete Sensor", tags = "Sensor")
    @DeleteMapping("/{id}")
    public ApiResponse<SensorDTO> deleteSensor(@PathVariable Long id,@NotNull @RequestHeader final String email)
    {
        long start = System.currentTimeMillis();
        SensorDTO savedEntity = sensorService.deleteSensor(id,email);
        return ApiResponse.created(savedEntity, (System.currentTimeMillis() - start) + " ms");
    }

}
