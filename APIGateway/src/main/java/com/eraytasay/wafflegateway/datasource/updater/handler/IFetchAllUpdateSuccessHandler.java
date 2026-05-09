package com.eraytasay.wafflegateway.datasource.updater.handler;

import com.eraytasay.wafflegateway.discovery.client.response.IFetchAllQueryResponseHandler;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface IFetchAllUpdateSuccessHandler<T> {
    void onUpdateSuccess(ResponseEntity<T> response, IFetchAllQueryResponseHandler<T> handler);
}
