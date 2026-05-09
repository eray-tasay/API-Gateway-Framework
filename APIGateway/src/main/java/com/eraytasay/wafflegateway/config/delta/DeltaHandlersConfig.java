package com.eraytasay.wafflegateway.config.delta;

import com.eraytasay.wafflegateway.datasource.updater.DeltaServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.datasource.updater.FetchAllServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.discovery.client.response.IDeltaQueryResponseHandler;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleDeltaResponse;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleFetchAllResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.time.Instant;

@Configuration
@ConditionalOnProperty(prefix = "api-gateway.delta", name = "enabled", havingValue = "true")
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class DeltaHandlersConfig {
    private static final Logger log = LoggerFactory.getLogger(DeltaHandlersConfig.class);

    private final DeltaServiceDataSourceUpdater<WaffleDeltaResponse> m_deltaUpdater;
    private final FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> m_fetchAllUpdater;
    private final TaskScheduler m_taskScheduler;

    @Value("${api-gateway.data-source-update.interval}")
    private Duration m_updateInterval;

    public DeltaHandlersConfig(DeltaServiceDataSourceUpdater<WaffleDeltaResponse> deltaUpdater,
                               FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> fetchAllUpdater,
                               TaskScheduler taskScheduler)
    {
        m_deltaUpdater = deltaUpdater;
        m_fetchAllUpdater = fetchAllUpdater;
        m_taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void setHandlers()
    {
        m_deltaUpdater.setSuccessHandler(this::successHandler);
        m_deltaUpdater.setFailureHandler(this::failureHandler);
    }

    private void successHandler(ResponseEntity<WaffleDeltaResponse> response, IDeltaQueryResponseHandler<WaffleDeltaResponse> handler)
    {
        var snapshotTimestamp = handler.getSnapshotTimestamp(response);

        m_taskScheduler.schedule(() -> m_deltaUpdater.update(snapshotTimestamp), Instant.now().plusMillis(m_updateInterval.toMillis()));
    }

    private void failureHandler(Exception ex)
    {
        log.error(ex.getMessage());

        m_taskScheduler.schedule(m_fetchAllUpdater::update, Instant.now().plusMillis(m_updateInterval.toMillis()));
    }
}
