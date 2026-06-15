package com.eraytasay.service.discovery.config;

import com.eraytasay.service.discovery.heartbeating.HeartBeatingRequestSender;
import com.eraytasay.service.discovery.register.handler.IRegistrationSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.time.Instant;

@AutoConfiguration
@ConditionalOnProperty(prefix = "auto-register", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RegistrationSuccessHandlerConfig {
    private static final Logger log = LoggerFactory.getLogger(RegistrationSuccessHandlerConfig.class);

    private final HeartBeatingRequestSender m_heartBeatingRequestSender;
    private final TaskScheduler m_taskScheduler;

    @Value("${heart-beating-interval}")
    private Duration m_heartBeatingInterval;

    public RegistrationSuccessHandlerConfig(HeartBeatingRequestSender heartBeatingRequestSender, TaskScheduler taskScheduler)
    {
        m_heartBeatingRequestSender = heartBeatingRequestSender;
        m_taskScheduler = taskScheduler;
    }

    @Bean
    public IRegistrationSuccessHandler registrationSuccessHandler()
    {
        var instant = Instant.now().plusMillis(m_heartBeatingInterval.toMillis());

        return () -> m_taskScheduler.scheduleAtFixedRate(this::heartbeat, instant, m_heartBeatingInterval);
    }

    private void heartbeat()
    {
        log.info("heartbeat scheduler is triggered.");

        try {
            m_heartBeatingRequestSender.send();
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }
}
