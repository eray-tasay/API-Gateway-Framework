package com.eraytasay.wafflegateway.task;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTasks implements ApplicationRunner {
    private final ServiceDataSourceInitTask m_serviceDataSourceInitTask;
    private final TaskScheduler m_taskScheduler;

    public ScheduleTasks(ServiceDataSourceInitTask serviceDataSourceInitTask, TaskScheduler taskScheduler)
    {
        m_serviceDataSourceInitTask = serviceDataSourceInitTask;
        m_taskScheduler = taskScheduler;
    }

    public void scheduleTasks()
    {
        m_serviceDataSourceInitTask.schedule(m_taskScheduler);
    }

    @Override
    public void run(ApplicationArguments args)
    {
        scheduleTasks();
    }
}
