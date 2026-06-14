package com.eraytasay.service.discovery.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@AutoConfiguration
public class TaskSchedulerConfig {
    @Bean
    public TaskScheduler taskScheduler()
    {
        var scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(1);

        return scheduler;
    }
}
