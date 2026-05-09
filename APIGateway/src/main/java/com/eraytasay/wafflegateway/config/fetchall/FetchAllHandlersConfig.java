package com.eraytasay.wafflegateway.config.fetchall;

import com.eraytasay.wafflegateway.datasource.updater.DeltaServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.datasource.updater.FetchAllServiceDataSourceUpdater;
import com.eraytasay.wafflegateway.datasource.updater.handler.IFetchAllUpdateFailureHandler;
import com.eraytasay.wafflegateway.datasource.updater.handler.IFetchAllUpdateSuccessHandler;
import com.eraytasay.wafflegateway.datasource.updater.handler.TryAgainFetchAllUpdateFailureHandler;
import com.eraytasay.wafflegateway.discovery.client.response.IFetchAllQueryResponseHandler;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleDeltaResponse;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleFetchAllResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.time.Instant;

@Configuration
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class FetchAllHandlersConfig {
    private static final Logger log = LoggerFactory.getLogger(FetchAllHandlersConfig.class);

    private final FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> m_fetchAllUpdater;
    private final DeltaServiceDataSourceUpdater<WaffleDeltaResponse> m_deltaUpdater;
    private final TaskScheduler m_taskScheduler;

    public FetchAllHandlersConfig(
            FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> fetchAllUpdater,
            ObjectProvider<DeltaServiceDataSourceUpdater<WaffleDeltaResponse>> provider,
            TaskScheduler taskScheduler)
    {
        m_fetchAllUpdater = fetchAllUpdater;
        m_taskScheduler = taskScheduler;
        m_deltaUpdater = provider.getIfAvailable();
    }

    @Value("${api-gateway.delta.enabled:false}")
    private boolean m_deltaFeatureEnabled;

    @Value("${api-gateway.data-source-update.interval}")
    private Duration m_updateInterval;

    @PostConstruct
    public void setHandlers()
    {
        var tryAgainFailureHandler = tryAgainFetchAllUpdateFailureHandler(m_fetchAllUpdater);

        m_fetchAllUpdater.setFailureHandler(tryAgainFailureHandler);

        if (m_deltaFeatureEnabled)
            m_fetchAllUpdater.setSuccessHandler(createSuccessHandlerForDelta(m_deltaUpdater, tryAgainFailureHandler));
        else
            m_fetchAllUpdater.setSuccessHandler(createSuccessHandlerForFetchAll(m_fetchAllUpdater, tryAgainFailureHandler));
    }

    private TryAgainFetchAllUpdateFailureHandler tryAgainFetchAllUpdateFailureHandler(FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> updater)
    {
        return new TryAgainFetchAllUpdateFailureHandler(createFailureHandler(updater), 3);
    }

    private IFetchAllUpdateSuccessHandler<WaffleFetchAllResponse> createSuccessHandlerForFetchAll(FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> updater,
                                                                                                  TryAgainFetchAllUpdateFailureHandler tryAgainFailureHandler)
    {
        return (ResponseEntity<WaffleFetchAllResponse> response, IFetchAllQueryResponseHandler<WaffleFetchAllResponse> handler) -> {
            var instant = Instant.now().plusMillis(m_updateInterval.toMillis());

            tryAgainFailureHandler.restartTryCount();
            m_taskScheduler.schedule(updater::update, instant);
        };
    }

    private IFetchAllUpdateSuccessHandler<WaffleFetchAllResponse> createSuccessHandlerForDelta(DeltaServiceDataSourceUpdater<WaffleDeltaResponse> updater,
                                                                                               TryAgainFetchAllUpdateFailureHandler tryAgainFailureHandler)
    {
        return (ResponseEntity<WaffleFetchAllResponse> response, IFetchAllQueryResponseHandler<WaffleFetchAllResponse> handler) -> {
            var snapshotTimestamp = handler.getSnapshotTimestamp(response);
            var instant = Instant.now().plusMillis(m_updateInterval.toMillis());

            tryAgainFailureHandler.restartTryCount();
            m_taskScheduler.schedule(() -> updater.update(snapshotTimestamp), instant);
        };
    }

    private IFetchAllUpdateFailureHandler createFailureHandler(FetchAllServiceDataSourceUpdater<WaffleFetchAllResponse> updater)
    {
        return (ex) -> {
            log.error(ex.getMessage());
            var instant = Instant.now().plusMillis(m_updateInterval.toMillis());

            m_taskScheduler.schedule(updater::update, instant);
        };
    }
}
