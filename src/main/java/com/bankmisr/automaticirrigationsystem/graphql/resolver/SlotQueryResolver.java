package com.bankmisr.automaticirrigationsystem.graphql.resolver;

import com.bankmisr.automaticirrigationsystem.graphql.filter.SlotFilter;
import com.bankmisr.automaticirrigationsystem.model.payload.SlotResponse;
import com.bankmisr.automaticirrigationsystem.service.SlotService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
@Slf4j
public class SlotQueryResolver implements GraphQLQueryResolver {

    private final SlotService slotService;
    private final HttpServletRequest httpRequest;

    public SlotResponse getSlots(SlotFilter filter) {

        log.info("<---- GraphQL getAllSlots --->");
        String email = this.httpRequest.getHeader("email");
        return slotService.fetchSlots(filter, email);
    }
}