package com.eraytasay.wafflegateway.config.task;

import com.eraytasay.wafflegateway.task.ScheduleTasks;
import com.eraytasay.wafflegateway.task.ServiceDataSourceInitTask;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;

@AutoConfiguration
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class ScheduleTasksConfig {
    private final ServiceDataSourceInitTask m_serviceDataSourceInitTask;
    private final TaskScheduler m_taskScheduler;

    public ScheduleTasksConfig(ServiceDataSourceInitTask serviceDataSourceInitTask, TaskScheduler taskScheduler)
    {
        m_serviceDataSourceInitTask = serviceDataSourceInitTask;
        m_taskScheduler = taskScheduler;
    }

    @Bean
    public ScheduleTasks scheduleTasks()
    {
        return new ScheduleTasks(m_serviceDataSourceInitTask, m_taskScheduler);
    }
}
