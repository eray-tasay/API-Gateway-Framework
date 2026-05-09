package com.eraytasay.wafflegateway.config;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.datasource.NotifyingServiceDataSource;
import com.eraytasay.wafflegateway.loadbalancer.manager.ListeningServiceLoadBalancerManager;
import com.eraytasay.wafflegateway.loadbalancer.manager.ServiceLoadBalancerManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalancerManagerConfig {
    @Bean
    @ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
    public ListeningServiceLoadBalancerManager listeningLoadBalancerManager(NotifyingServiceDataSource dataSource)
    {
        var manager = ListeningServiceLoadBalancerManager.of(dataSource);

        dataSource.subscribe(manager);

        return manager;
    }

    @Bean
    @ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "false")
    public ServiceLoadBalancerManager loadBalancerManager(IServiceDataSource dataSource)
    {
        return ServiceLoadBalancerManager.of(dataSource);
    }
}
