package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.loadbalancer.manager.IServiceLoadBalancerManager;
import com.eraytasay.wafflegateway.rpf.filter.chain.LoadBalancerRequestProvider;
import com.eraytasay.wafflegateway.rpf.filter.chain.SimpleRequestProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RequestProviderConfig {
    @Bean
    @ConditionalOnBean(IServiceLoadBalancerManager.class)
    public LoadBalancerRequestProvider loadBalancerRequestProvider(IServiceLoadBalancerManager serviceLoadBalancerManager)
    {
        return new LoadBalancerRequestProvider(serviceLoadBalancerManager);
    }

    @Bean
    @ConditionalOnMissingBean(IServiceLoadBalancerManager.class)
    public SimpleRequestProvider simpleRequestProvider()
    {
        return new SimpleRequestProvider();
    }
}
