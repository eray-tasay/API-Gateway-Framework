package com.eraytasay.wafflegateway.discovery.client.response;

import com.eraytasay.wafflegateway.datasource.IServiceDataSource;
import org.springframework.http.ResponseEntity;

public interface IFetchAllQueryResponseHandler<T> {
    IServiceDataSource getDataSource(ResponseEntity<T> fetchAllQueryResponse);
    long getSnapshotTimestamp(ResponseEntity<T> fetchAllQueryResponse);
}
