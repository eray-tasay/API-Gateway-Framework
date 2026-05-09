package com.eraytasay.wafflegateway.config;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.datasource.NotifyingServiceDataSource;
import com.eraytasay.wafflegateway.datasource.ServiceDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceDataSourceConfig {
    @Bean
    @ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
    public NotifyingServiceDataSource notifyingServiceDataSource()
    {
        return NotifyingServiceDataSource.of();
    }

    @Bean
    @ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "false")
    public IServiceDataSource serviceDataSource()
    {
        return ServiceDataSource.of();
    }
}
