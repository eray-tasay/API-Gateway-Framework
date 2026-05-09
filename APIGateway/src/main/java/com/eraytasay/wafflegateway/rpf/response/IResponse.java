package com.eraytasay.wafflegateway.rpf.response;

import com.eraytasay.wafflegateway.rpf.response.header.IResponseHeaders;
import org.springframework.http.HttpStatus;

public interface IResponse {
    HttpStatus getStatusCode();
    IResponseHeaders getHeaders();
    byte[] getBody();
}
