package com.eraytasay.wafflegateway.config.task;

import com.eraytasay.wafflegateway.datasource.updater.FetchAllServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleFetchAllResponse;
import com.eraytasay.wafflegateway.task.ServiceDataSourceInitTask;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class TaskConfig {
    private final FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> m_updater;

    public TaskConfig(FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> updater)
    {
        m_updater = updater;
    }

    @Bean
    public ServiceDataSourceInitTask serviceDataSourceInitTask()
    {
        return new ServiceDataSourceInitTask(m_updater);
    }
}
