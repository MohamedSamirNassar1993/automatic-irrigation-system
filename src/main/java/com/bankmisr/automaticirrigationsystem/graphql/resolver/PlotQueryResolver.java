package com.bankmisr.automaticirrigationsystem.graphql.resolver;

import com.bankmisr.automaticirrigationsystem.graphql.filter.PlotFilter;
import com.bankmisr.automaticirrigationsystem.model.payload.PlotResponse;
import com.bankmisr.automaticirrigationsystem.service.PlotService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
@Slf4j
public class PlotQueryResolver implements GraphQLQueryResolver {

    private final PlotService plotService;
    private final HttpServletRequest httpRequest;

    public PlotResponse getPlots(PlotFilter filter) {

        log.info("<---- GraphQL getAllPlots --->");
        String email = this.httpRequest.getHeader("email");
        return plotService.fetchPlots(filter, email);
    }
}
