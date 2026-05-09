package com.eraytasay.service.discovery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class UpdateCounterConfig {
    @Bean
    public AtomicLong updateCounter()
    {
        return new AtomicLong();
    }
}
