package com.eraytasay.wafflegateway.task;

import com.eraytasay.wafflegateway.datasource.updater.FetchAllServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleFetchAllResponse;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ServiceDataSourceInitTask {
    private final FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> m_updater;

    public ServiceDataSourceInitTask(FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> updater)
    {
        m_updater = updater;
    }

    public void schedule(TaskScheduler taskScheduler)
    {
        taskScheduler.schedule(this::apply, Instant.now());
    }

    public void apply()
    {
        m_updater.update();
    }
}
