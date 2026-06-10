package com.eraytasay.wafflegateway.config.fetchall;

import com.eraytasay.wafflegateway.datasource.NotifyingServiceDataSource;
import com.eraytasay.wafflegateway.datasource.updater.FetchAllServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.discovery.waffle.client.WaffleFetchAllServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleFetchAllResponse;
import com.eraytasay.wafflegateway.discovery.waffle.response.handler.WaffleFetchAllQueryResponseHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class FetchAllUpdaterConfig {
    private final NotifyingServiceDataSource m_serviceDataSource;
    private final WaffleFetchAllServiceDiscoveryClient m_client;
    private final WaffleFetchAllQueryResponseHandler m_responseHandler;

    public FetchAllUpdaterConfig(NotifyingServiceDataSource serviceDataSource,
                                 WaffleFetchAllServiceDiscoveryClient client,
                                 WaffleFetchAllQueryResponseHandler responseHandler)
    {
        m_serviceDataSource = serviceDataSource;
        m_client = client;
        m_responseHandler = responseHandler;
    }

    @Bean
    public FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> fetchAllUpdater()
    {
        var updater = new FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse>();

        updater.setClient(m_client);
        updater.setServiceDataSource(m_serviceDataSource);
        updater.setResponseHandler(m_responseHandler);

        return updater;
    }
}
