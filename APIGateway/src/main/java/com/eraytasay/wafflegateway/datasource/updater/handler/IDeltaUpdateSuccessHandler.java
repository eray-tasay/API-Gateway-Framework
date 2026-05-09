package com.eraytasay.wafflegateway.datasource.updater.handler;

import com.eraytasay.wafflegateway.discovery.client.response.IDeltaQueryResponseHandler;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface IDeltaUpdateSuccessHandler<T> {
    void onUpdateSuccess(ResponseEntity<T> response, IDeltaQueryResponseHandler<T> handler);
}
