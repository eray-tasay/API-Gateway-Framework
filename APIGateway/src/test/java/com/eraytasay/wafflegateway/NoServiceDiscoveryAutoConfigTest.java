package com.eraytasay.wafflegateway;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.datasource.ServiceDataSource;
import com.eraytasay.wafflegateway.datasource.updater.IServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.discovery.client.IDeltaServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.client.IFetchAllServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.client.response.IDeltaQueryResponseHandler;
import com.eraytasay.wafflegateway.discovery.client.response.IFetchAllQueryResponseHandler;
import com.eraytasay.wafflegateway.loadbalancer.manager.ServiceLoadBalancerManager;
import com.eraytasay.wafflegateway.rpf.filter.chain.LoadBalancerRequestProvider;
import com.eraytasay.wafflegateway.task.ScheduleTasks;
import com.eraytasay.wafflegateway.task.ServiceDataSourceInitTask;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class NoServiceDiscoveryAutoConfigTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(TestConfig.class);

    @Test
    void should_not_create_beans_when_disabled()
    {
        contextRunner
                .withPropertyValues("api-gateway.service-discovery.enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(IServiceDataSourceUpdater.class);
                    assertThat(context).doesNotHaveBean(IDeltaServiceDiscoveryClient.class);
                    assertThat(context).doesNotHaveBean(IFetchAllServiceDiscoveryClient.class);
                    assertThat(context).doesNotHaveBean(IDeltaQueryResponseHandler.class);
                    assertThat(context).doesNotHaveBean(IFetchAllQueryResponseHandler.class);
                    assertThat(context).doesNotHaveBean("dataSourceUpdaterScheduler");
                    assertThat(context).doesNotHaveBean(ServiceDataSourceInitTask.class);
                    assertThat(context).doesNotHaveBean(ScheduleTasks.class);
                    assertThat(context).doesNotHaveBean(IServiceDataSource.class);
                    assertThat(context).doesNotHaveBean(ServiceLoadBalancerManager.class);
                    assertThat(context).doesNotHaveBean(LoadBalancerRequestProvider.class);
                });
    }

    @Test
    void should_create_beans_when_disabled()
    {
        contextRunner
                .withBean(IServiceDataSource.class, ServiceDataSource::of)
                .withPropertyValues("api-gateway.service-discovery.enabled=false")
                .run(context -> {
                    assertThat(context).hasSingleBean(ServiceLoadBalancerManager.class);
                    assertThat(context).hasSingleBean(LoadBalancerRequestProvider.class);
                });
    }
}
