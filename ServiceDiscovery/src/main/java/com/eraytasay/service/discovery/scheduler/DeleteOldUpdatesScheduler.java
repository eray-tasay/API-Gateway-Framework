package com.eraytasay.service.discovery.scheduler;

import com.eraytasay.service.discovery.service.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

public class DeleteOldUpdatesScheduler {
    private static final Logger log = LoggerFactory.getLogger(DeleteOldUpdatesScheduler.class);

    private final UpdateService m_updateService;
    private final TaskScheduler m_taskScheduler;
    private final Duration m_interval;

    private ScheduledFuture<?> m_task;

    public DeleteOldUpdatesScheduler(UpdateService updateService, TaskScheduler taskScheduler, Duration interval)
    {
        m_updateService = updateService;
        m_taskScheduler = taskScheduler;
        m_interval = interval;
    }

    public void schedule()
    {
        m_task = m_taskScheduler.scheduleAtFixedRate(this::deleteOldUpdates, m_interval);
    }

    public void cancel()
    {
        if (m_task == null)
            throw new UnsupportedOperationException("Task has not been scheduled.");

        m_task.cancel(true);
    }

    private void deleteOldUpdates()
    {
        log.info("deleteOldUpdates scheduler is triggered");

        try {
            m_updateService.deleteOldUpdates();
        }
        catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }
}
