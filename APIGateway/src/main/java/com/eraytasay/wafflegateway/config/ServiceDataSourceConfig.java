package com.eraytasay.wafflegateway.config;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.datasource.NotifyingServiceDataSource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnMissingBean(IServiceDataSource.class)
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class ServiceDataSourceConfig {
    @Bean
    public NotifyingServiceDataSource notifyingServiceDataSource()
    {
        return NotifyingServiceDataSource.of();
    }
}
