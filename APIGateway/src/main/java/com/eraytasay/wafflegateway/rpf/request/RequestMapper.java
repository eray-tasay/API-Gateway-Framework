package com.eraytasay.wafflegateway.rpf.request;

import com.eraytasay.wafflegateway.rpf.request.header.RequestHeaders;
import com.eraytasay.wafflegateway.rpf.request.param.QueryParameters;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public final class RequestMapper {
    public Request map(HttpServletRequest servletRequest)
    {
        if (servletRequest == null)
            throw new IllegalArgumentException("HttpServletRequest cannot be null");

        var method = HttpMethod.valueOf(servletRequest.getMethod().toUpperCase());
        var path = servletRequest.getRequestURI();
        var headers = extractHeaders(servletRequest);
        var queryParameters = extractQueryParameters(servletRequest);
        var body = extractBody(servletRequest);

        return Request.builder()
                .method(method)
                .host(servletRequest.getRemoteHost())
                .scheme(servletRequest.getScheme())
                .port(servletRequest.getRemotePort())
                .path(path)
                .headers(headers)
                .queryParameters(queryParameters)
                .body(body)
                .build();
    }

    private static RequestHeaders extractHeaders(HttpServletRequest request)
    {
        var headers = RequestHeaders.of();
        var headerNames = Collections.list(request.getHeaderNames());
        
        for (var headerName : headerNames) {
            var headerValues = Collections.list(request.getHeaders(headerName));

            headerValues.forEach(val -> headers.mutate(headerName).addLast(val));
        }

        return headers;
    }

    private static QueryParameters extractQueryParameters(HttpServletRequest request)
    {
        var params = QueryParameters.of();
        var paramMap = request.getParameterMap();

        for (var param : paramMap.entrySet()) {
            var paramName = param.getKey();
            var paramValues = param.getValue();

            Arrays.stream(paramValues).forEach(val -> params.mutate(paramName).addLast(val));
        }

        return params;
    }

    private static byte[] extractBody(HttpServletRequest request)
    {
        try (var inputStream = request.getInputStream()) {
            if (inputStream == null)
                return new byte[0];

            return StreamUtils.copyToByteArray(inputStream);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}