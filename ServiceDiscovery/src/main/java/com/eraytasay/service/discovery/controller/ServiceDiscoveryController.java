package com.eraytasay.service.discovery.controller;

import com.eraytasay.service.discovery.dto.heartbeating.HeartBeatingRequestDto;
import com.eraytasay.service.discovery.dto.response.ResponseDto;
import com.eraytasay.service.discovery.dto.response.ResponseType;
import com.eraytasay.service.discovery.dto.serverinstance.UnregisterServiceDto;
import com.eraytasay.service.discovery.dto.serverinstance.ServerInstanceRegisterDto;
import com.eraytasay.service.discovery.dto.serverinstance.ServicesDto;
import com.eraytasay.service.discovery.service.ServiceInstanceService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service-discovery")
public class ServiceDiscoveryController {
    private static final Logger log = LoggerFactory.getLogger(ServiceDiscoveryController.class);

    private final ServiceInstanceService m_serviceInstanceService;

    public ServiceDiscoveryController(ServiceInstanceService serviceInstanceService)
    {
        m_serviceInstanceService = serviceInstanceService;
    }

    @GetMapping("/services")
    public ResponseEntity<ResponseDto<ServicesDto>> services()
    {
        log.info("Request arrived to /services.");

        var services = m_serviceInstanceService.findAll();

        return ResponseEntity.ok(new ResponseDto<>(ResponseType.SUCCESS, services));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Void>> register(HttpServletRequest request, @RequestBody ServerInstanceRegisterDto serverInstanceRegisterDto)
    {
        log.info("Request arrived to /register.");

        m_serviceInstanceService.register(request.getRemoteAddr(), serverInstanceRegisterDto);

        return ResponseEntity.ok(
                new ResponseDto<>("Registered successfully.", ResponseType.SUCCESS)
        );
    }

    @PostMapping("/listen")
    public ResponseEntity<ResponseDto<Void>> listen(HttpServletRequest request, @RequestBody HeartBeatingRequestDto heartBeatingRequestDto)
    {
        log.info("Request arrived to /listen.");

        m_serviceInstanceService.processHeartBeatingRequest(request.getRemoteAddr(), heartBeatingRequestDto);

        return ResponseEntity.ok(
                new ResponseDto<>("Heart beating request is processed successfully.", ResponseType.SUCCESS)
        );
    }

    @PostMapping("/unregister")
    public ResponseEntity<ResponseDto<Void>> unregister(HttpServletRequest request, @RequestBody UnregisterServiceDto unregisterServiceDto)
    {
        log.info("Request arrived to /unregister");

        m_serviceInstanceService.unregister(request.getRemoteAddr(), unregisterServiceDto);

        return ResponseEntity.ok(
                new ResponseDto<>("Service is unregistered successfully.", ResponseType.SUCCESS)
        );
    }
}
