package com.eraytasay.wafflegateway.rpf.request;

import com.eraytasay.wafflegateway.rpf.request.header.IRequestHeaders;
import com.eraytasay.wafflegateway.rpf.request.param.IQueryParameters;
import org.springframework.http.HttpMethod;

public interface IRequest {
    String getPath();
    HttpMethod getMethod();
    IRequestHeaders getHeaders();
    IQueryParameters getQueryParameters();
    byte[] getBody();
}
