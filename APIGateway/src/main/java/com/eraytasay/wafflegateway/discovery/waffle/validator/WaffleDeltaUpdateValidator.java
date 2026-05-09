package com.eraytasay.wafflegateway.discovery.waffle.validator;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.datasource.validator.IDeltaUpdateValidator;
import com.eraytasay.wafflegateway.discovery.client.response.IDeltaQueryResponseHandler;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleDeltaResponse;
import com.eraytasay.wafflegateway.exception.DeltaUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class WaffleDeltaUpdateValidator implements IDeltaUpdateValidator<WaffleDeltaResponse> {
    private static final Logger log = LoggerFactory.getLogger(WaffleDeltaUpdateValidator.class);

    private final IServiceDataSource m_serviceDataSource;
    private long m_lastDeltaVersion;

    public WaffleDeltaUpdateValidator(IServiceDataSource serviceDataSource)
    {
        m_serviceDataSource = serviceDataSource;
        m_lastDeltaVersion = -1;
    }

    @Override
    public void validate(ResponseEntity<WaffleDeltaResponse> response, IDeltaQueryResponseHandler<WaffleDeltaResponse> handler)
    {
        var data = response.getBody().getData();
        var serverDeltaVersion = data.getDeltaVersion();
        var numberOfUpdates = data.getUpdates().size();

        var serverNumberOfServices = data.getNumberOfServices();
        var clientDeltaVersion = m_lastDeltaVersion + numberOfUpdates;
        var clientNumberOfServices = m_serviceDataSource.size();

        checkNumberOfServices(clientNumberOfServices, serverNumberOfServices);

        if (m_lastDeltaVersion == -1) {
            m_lastDeltaVersion = serverDeltaVersion;
            log.info("Delta update is done successfully. The numbers of services match exactly: {}", clientNumberOfServices);
            return;
        }

        checkDeltaVersions(clientDeltaVersion, serverDeltaVersion);
        m_lastDeltaVersion = serverDeltaVersion;

        log.info("Delta update is done successfully. Both the number of services ({}) and delta version ({}) are correct", clientNumberOfServices, clientDeltaVersion);
    }

    private static void checkDeltaVersions(long client, long server)
    {
        if (client != server)
            throw new DeltaUpdateException(("""
                    Delta update problem: Delta versions do not match. Client %d but server %d.\s
                    API Gateway missed %d delta updates.""")
                    .formatted(client, server, server - client));

    }

    private static void checkNumberOfServices(int client, int server)
    {
        if (client != server)
            throw new DeltaUpdateException("Delta update problem: The numbers of services do not match. Client %d but server %d"
                    .formatted(client, server));
    }
}
