package com.eraytasay.service.discovery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/sample")
    public String sample()
    {
        log.info("/sample endpoint is called");

        return "sample";
    }

    @GetMapping("/mample")
    public String mample()
    {
        log.info("/mample endpoint is called");

        return "mample";
    }
}
