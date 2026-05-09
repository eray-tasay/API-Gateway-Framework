package com.eraytasay.service.discovery.config;

import com.eraytasay.service.discovery.scheduler.DeleteOldUpdatesScheduler;
import com.eraytasay.service.discovery.service.UpdateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;

@Configuration
public class DeleteOldUpdatesSchedulerConfig {
    private final UpdateService m_updateService;
    private final TaskScheduler m_taskScheduler;

    @Value("${service-discovery.old-updates.scan-interval}")
    private Duration m_interval;

    public DeleteOldUpdatesSchedulerConfig(UpdateService updateService, TaskScheduler taskScheduler)
    {
        m_updateService = updateService;
        m_taskScheduler = taskScheduler;
    }

    @Bean
    public DeleteOldUpdatesScheduler deleteOldUpdatesScheduler()
    {
        var scheduler = new DeleteOldUpdatesScheduler(m_updateService, m_taskScheduler, m_interval);

        scheduler.schedule();

        return scheduler;
    }
}
