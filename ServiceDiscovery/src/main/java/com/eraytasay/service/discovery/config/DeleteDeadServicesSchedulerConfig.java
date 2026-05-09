package com.eraytasay.service.discovery.config;

import com.eraytasay.service.discovery.scheduler.DeleteDeadServicesScheduler;
import com.eraytasay.service.discovery.service.ServiceInstanceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;

@Configuration
public class DeleteDeadServicesSchedulerConfig {
    private final ServiceInstanceService m_serviceInstanceService;
    private final TaskScheduler m_taskScheduler;

    @Value("${service-discovery.dead-services.scan-interval}")
    private Duration m_scanInterval;

    public DeleteDeadServicesSchedulerConfig(ServiceInstanceService serviceInstanceService, TaskScheduler taskScheduler)
    {
        m_serviceInstanceService = serviceInstanceService;
        m_taskScheduler = taskScheduler;
    }

    @Bean
    public DeleteDeadServicesScheduler deleteDeadServicesScheduler()
    {
        var scheduler = new DeleteDeadServicesScheduler(m_serviceInstanceService, m_taskScheduler, m_scanInterval);

        scheduler.schedule();

        return scheduler;
    }
}
