package com.eraytasay.service.discovery.scheduler;

import com.eraytasay.service.discovery.service.ServiceInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

public class DeleteDeadServicesScheduler {
    private static final Logger log = LoggerFactory.getLogger(DeleteDeadServicesScheduler.class);

    private final ServiceInstanceService m_serviceInstanceService;
    private final Duration m_scanInterval;
    private final TaskScheduler m_taskScheduler;

    private ScheduledFuture<?> m_task;

    public DeleteDeadServicesScheduler(ServiceInstanceService serviceInstanceService, TaskScheduler taskScheduler, Duration scanInterval)
    {
        m_serviceInstanceService = serviceInstanceService;
        m_scanInterval = scanInterval;
        m_taskScheduler = taskScheduler;
    }

    public void schedule()
    {
        m_task = m_taskScheduler.scheduleAtFixedRate(this::deleteDeadServices, m_scanInterval);
    }

    public void cancel()
    {
        if (m_task == null)
            throw new UnsupportedOperationException("Task has not been scheduled.");

        m_task.cancel(true);
    }

    private void deleteDeadServices()
    {
        log.info("deleteDeadServices scheduler is triggered");

        try {
            m_serviceInstanceService.deleteDeadServices();
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }
}
