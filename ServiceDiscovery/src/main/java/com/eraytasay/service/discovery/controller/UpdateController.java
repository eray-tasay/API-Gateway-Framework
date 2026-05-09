package com.eraytasay.service.discovery.controller;

import com.eraytasay.service.discovery.dto.response.ResponseDto;
import com.eraytasay.service.discovery.dto.response.ResponseType;
import com.eraytasay.service.discovery.dto.update.UpdatesDto;
import com.eraytasay.service.discovery.service.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/service-discovery")
public class UpdateController {
    private static final Logger log = LoggerFactory.getLogger(UpdateController.class);

    private final UpdateService m_updateService;

    public UpdateController(UpdateService updateService)
    {
        m_updateService = updateService;
    }

    @GetMapping("/updates")
    public ResponseEntity<ResponseDto<UpdatesDto>> updates(@RequestParam(name = "after", defaultValue = "1") long after)
    {
        log.info("Request arrived to /updates.");

        var updates = m_updateService.findAfter(after);

        return ResponseEntity.ok(
                new ResponseDto<>(ResponseType.SUCCESS, updates)
        );
    }
}
