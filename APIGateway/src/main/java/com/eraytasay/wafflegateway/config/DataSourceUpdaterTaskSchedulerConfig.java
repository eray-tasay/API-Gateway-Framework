package com.eraytasay.wafflegateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class DataSourceUpdaterTaskSchedulerConfig {
    private static final Logger log = LoggerFactory.getLogger("dataSourceUpdaterScheduler");

    private final ConfigurableApplicationContext m_context;

    public DataSourceUpdaterTaskSchedulerConfig(ConfigurableApplicationContext context)
    {
        m_context = context;
    }

    @Bean
    public TaskScheduler dataSourceUpdaterScheduler()
    {
        var scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("dataSourceUpdaterScheduler-");

        scheduler.setErrorHandler(t -> {
            log.error("API gateway cannot reach service discovery. Application has been terminated!");

            Thread.ofPlatform().start(() -> {
                scheduler.shutdown();
                shutdown();
            });
        });

        scheduler.setTaskDecorator(getTaskDecorator());
        scheduler.initialize();

        return scheduler;
    }

    private TaskDecorator getTaskDecorator()
    {
        return new TaskDecorator() {
            @Override
            public Runnable decorate(Runnable runnable)
            {
                return () -> {
                    log.info("dataSourceUpdaterScheduler is triggered");
                    runnable.run();
                };
            }
        };
    }

    private void shutdown()
    {
        int exitCode = SpringApplication.exit(m_context, () -> 0);

        System.exit(exitCode);
    }
}
