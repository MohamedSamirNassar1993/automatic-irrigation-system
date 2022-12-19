package com.bankmisr.automaticirrigationsystem.graphql.resolver;

import com.bankmisr.automaticirrigationsystem.graphql.filter.SensorFilter;
import com.bankmisr.automaticirrigationsystem.model.payload.SensorResponse;
import com.bankmisr.automaticirrigationsystem.service.SensorService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
@Slf4j
public class SensorQueryResolver implements GraphQLQueryResolver {

    private final SensorService sensorService;
    private final HttpServletRequest httpRequest;

    public SensorResponse getSensors(SensorFilter filter) {

        log.info("<---- GraphQL getAllSensors --->");
        String email = this.httpRequest.getHeader("email");
        return sensorService.fetchSensors(filter, email);
    }
}
