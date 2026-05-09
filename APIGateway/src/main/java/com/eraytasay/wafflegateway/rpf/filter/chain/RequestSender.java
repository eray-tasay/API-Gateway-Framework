package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.request.IRequest;
import com.eraytasay.wafflegateway.rpf.response.Response;
import com.eraytasay.wafflegateway.rpf.response.header.ResponseHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.stream.StreamSupport;

@Component
public class RequestSender {
    private final RestClient m_restClient;

    public RequestSender(RestClient restClient)
    {
        m_restClient = restClient;
    }

    public Response sendRequest(IRequest request)
    {
        var responseEntity = executeRequest(request);

        return createResponse(responseEntity);
    }

    private ResponseEntity<byte[]> executeRequest(IRequest request)
    {
        return m_restClient
                .method(request.getMethod())
                .uri(uriBuilder -> prepareUri(uriBuilder, request))
                .headers(headers -> prepareHeaders(headers, request))
                .body(getBody(request))
                .retrieve()
                .toEntity(byte[].class);
    }

    private static Response createResponse(ResponseEntity<byte[]> responseEntity)
    {
        return Response.builder()
                .statusCode(HttpStatus.valueOf(responseEntity.getStatusCode().value()))
                .body(responseEntity.getBody())
                .headers(createResponseHeaders(responseEntity))
                .build();
    }

    private static ResponseHeaders createResponseHeaders(ResponseEntity<?> responseEntity)
    {
        var res = new ResponseHeaders();

        responseEntity.getHeaders().forEach((name, values) -> {
            values.forEach(value -> res.mutate(name).addLast(value));
        });

        return res;
    }

    private static URI prepareUri(UriBuilder uriBuilder, IRequest request)
    {
        var host = request.getHeaders().get("Host").getFirst();

        uriBuilder.scheme("http");
        uriBuilder.host(host);
        uriBuilder.path(request.getPath());

        request.getQueryParameters().getNames().forEach(name -> {
            var param = request.getQueryParameters().get(name).getAll();

            uriBuilder.queryParam(name, StreamSupport.stream(param.spliterator(), false));
        });

        return uriBuilder.build();
    }

    private static void prepareHeaders(HttpHeaders headers, IRequest request)
    {
        request.getHeaders().getNames().forEach(name -> {
            request.getHeaders().get(name).getAll().forEach(value -> headers.add(name, value));
        });
    }

    private static byte[] getBody(IRequest request)
    {
        return request.getBody() != null ? request.getBody() : new byte[0];
    }
}
