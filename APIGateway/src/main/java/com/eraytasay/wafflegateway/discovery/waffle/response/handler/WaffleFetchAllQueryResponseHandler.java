package com.eraytasay.wafflegateway.discovery.waffle.response.handler;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import com.eraytasay.wafflegateway.datasource.ServiceDataSource;
import com.eraytasay.wafflegateway.discovery.client.response.IFetchAllQueryResponseHandler;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleFetchAllResponse;
import org.springframework.http.ResponseEntity;

public class WaffleFetchAllQueryResponseHandler implements IFetchAllQueryResponseHandler<WaffleFetchAllResponse> {
    @Override
    public IServiceDataSource getDataSource(ResponseEntity<WaffleFetchAllResponse> response)
    {
        var body = response.getBody();
        var services = body.getData().getServices();

        return ServiceDataSource.of(services);
    }

    @Override
    public long getSnapshotTimestamp(ResponseEntity<WaffleFetchAllResponse> response)
    {
        var body = response.getBody();

        return body.getData().getSnapshotTimestamp();
    }
}