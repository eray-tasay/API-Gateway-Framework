package com.eraytasay.wafflegateway.config.delta;

import com.eraytasay.wafflegateway.datasource.NotifyingServiceDataSource;
import com.eraytasay.wafflegateway.datasource.updater.DeltaServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.discovery.waffle.validator.WaffleDeltaUpdateValidator;
import com.eraytasay.wafflegateway.discovery.waffle.client.WaffleDeltaServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleDeltaResponse;
import com.eraytasay.wafflegateway.discovery.waffle.response.handler.WaffleDeltaQueryResponseHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "api-gateway.delta", name = "enabled", havingValue = "true")
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class DeltaUpdaterConfig {
    private final NotifyingServiceDataSource m_serviceDataSource;
    private final WaffleDeltaServiceDiscoveryClient m_client;
    private final WaffleDeltaQueryResponseHandler m_responseHandler;

    public DeltaUpdaterConfig(NotifyingServiceDataSource serviceDataSource, WaffleDeltaServiceDiscoveryClient client,
                              WaffleDeltaQueryResponseHandler responseHandler)
    {
        m_serviceDataSource = serviceDataSource;
        m_client = client;
        m_responseHandler = responseHandler;
    }

    @Bean
    public DeltaServiceDataSourceUpdater<WaffleDeltaResponse> deltaUpdater()
    {
        var updater = new DeltaServiceDataSourceUpdater<WaffleDeltaResponse>();

        updater.setClient(m_client);
        updater.setResponseHandler(m_responseHandler);
        updater.setServiceDataSource(m_serviceDataSource);
        updater.setDeltaUpdateValidator(createDeltaUpdateValidator());

        return updater;
    }

    private WaffleDeltaUpdateValidator createDeltaUpdateValidator()
    {
        return new WaffleDeltaUpdateValidator(m_serviceDataSource);
    }
}
