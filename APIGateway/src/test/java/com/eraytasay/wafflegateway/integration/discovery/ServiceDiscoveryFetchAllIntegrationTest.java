package com.eraytasay.wafflegateway.integration.discovery;

import com.eraytasay.wafflegateway.discovery.waffle.client.WaffleFetchAllServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.waffle.response.handler.WaffleFetchAllQueryResponseHandler;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@WireMockTest
class ServiceDiscoveryFetchAllIntegrationTest {
    private WaffleFetchAllServiceDiscoveryClient client;
    private WaffleFetchAllQueryResponseHandler responseHandler;

    @BeforeEach
    void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        var restClient = RestClient.builder()
                .baseUrl(wmRuntimeInfo.getHttpBaseUrl())
                .build();

        client = new WaffleFetchAllServiceDiscoveryClient();
        client.setRestClient(restClient);
        client.setUrl("/services");

        responseHandler = new WaffleFetchAllQueryResponseHandler();
    }

    @Test
    void fetchServices_ShouldRetrieveDataFromRealHttpEndpoint() {
        var jsonResponse = """
            {
                "type": "SUCCESS",
                "data": {
                    "services": [
                        {
                            "serviceId": "10.0.0.5:8080",
                            "address": "10.0.0.5",
                            "port": 8080,
                            "serviceName": "payment-service",
                            "loadBalancingAlgorithm": "ROUND_ROBIN",
                            "lastHeartBeatTime": 10329232
                        },
                        {
                            "serviceId": "10.0.0.6:8081",
                            "address": "10.0.0.6",
                            "port": 8081,
                            "serviceName": "payment-service",
                            "loadBalancingAlgorithm": "ROUND_ROBIN",
                            "lastHeartBeatTime": 10329232
                        },
                        {
                            "serviceId": "10.0.0.7:8082",
                            "address": "10.0.0.7",
                            "port": 8082,
                            "serviceName": "order-service",
                            "loadBalancingAlgorithm": "LEAST_CONNECTION",
                            "lastHeartBeatTime": 10329232
                        }
                    ],
                    "snapshotTimestamp": 10329210
                },
                "timestamp": 10329232
            }
            """;

        stubFor(get(urlEqualTo("/services"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));

        var response = client.fetchAll();
        var dataSource = responseHandler.getDataSource(response);

        assertFalse(dataSource.getService("10.0.0.5:8080").isEmpty());
        assertFalse(dataSource.getService("10.0.0.6:8081").isEmpty());
        assertFalse(dataSource.getService("10.0.0.7:8082").isEmpty());

        verify(getRequestedFor(urlEqualTo("/services")));
    }
}
