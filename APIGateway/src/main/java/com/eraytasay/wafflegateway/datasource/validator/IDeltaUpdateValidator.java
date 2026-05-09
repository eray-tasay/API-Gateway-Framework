package com.eraytasay.wafflegateway.datasource.validator;

import com.eraytasay.wafflegateway.discovery.client.response.IDeltaQueryResponseHandler;
import com.eraytasay.wafflegateway.exception.DeltaUpdateException;
import org.springframework.http.ResponseEntity;

public interface IDeltaUpdateValidator<T> {
    void validate(ResponseEntity<T> response, IDeltaQueryResponseHandler<T> handler) throws DeltaUpdateException;
}
