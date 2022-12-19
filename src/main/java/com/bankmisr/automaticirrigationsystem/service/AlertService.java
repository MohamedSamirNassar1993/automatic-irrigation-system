package com.bankmisr.automaticirrigationsystem.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AlertService {
    public void sendAlert(String message) {

        log.info("sending alert to admin: {}", message);
    }
}
