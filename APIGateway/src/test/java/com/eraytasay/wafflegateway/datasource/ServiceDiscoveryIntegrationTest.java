package com.eraytasay.wafflegateway.datasource;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*
@WireMockTest // Bu anatasyon test sırasında 8080 (veya rastgele) portunda bir server açar
class ServiceDiscoveryIntegrationTest {
    private x provider;
    private RestClient restClient;

    @BeforeEach
    void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        // 1. Gerçek bir RestClient oluşturuyoruz
        restClient = RestClient.builder()
                .baseUrl(wmRuntimeInfo.getHttpBaseUrl())
                .build();

        // 2. Test edeceğimiz sınıfı manuel kuruyoruz
        provider = new x();
        provider.setRestClient(restClient);
        provider.setUrl("/api/v1/services"); // Mock sunucudaki endpoint
    }

    @Test
    void fetchServicesSync_ShouldRetrieveDataFromRealHttpEndpoint() {
        // --- GIVEN (WireMock ile sahte sunucu cevabı hazırlıyoruz) ---
        String jsonResponse = """
            {
                "type": "SUCCESS",
                "data": {
                    "payment-service": [
                        {
                            "serviceId": "10.0.0.5:8080",
                            "address": "10.0.0.5",
                            "port": 8080,
                            "serviceName": "payment-service",
                            "loadBalancingAlgorithm": "round-robin",
                            "lastHeartBeatTime": 10329232
                        },
                        {
                            "serviceId": "10.0.0.6:8081",
                            "address": "10.0.0.6",
                            "port": 8081,
                            "serviceName": "payment-service",
                            "loadBalancingAlgorithm": "round-robin",
                            "lastHeartBeatTime": 10329232
                        }
                    ],
                    "order-service": [
                        {
                            "serviceId": "10.0.0.7:8082",
                            "address": "10.0.0.7",
                            "port": 8082,
                            "serviceName": "order-service",
                            "loadBalancingAlgorithm": "round-robin",
                            "lastHeartBeatTime": 10329232
                        }
                    ]
                },
                "timestamp": 10329232
            }
            """;

        stubFor(get(urlEqualTo("/api/v1/services"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));

        // --- WHEN (Gerçek network çağrısı yapılıyor) ---
        provider.fetchServicesSync();

        // --- THEN (Dönen verinin doğruluğu) ---
        assertFalse(provider.getService("payment-service", "10.0.0.5:8080").isEmpty());
        assertFalse(provider.getService("payment-service", "10.0.0.6:8081").isEmpty());
        assertFalse(provider.getService("order-service", "10.0.0.7:8082").isEmpty());

        // Sunucuya gerçekten istek gittiğini WireMock üzerinden doğruluyoruz
        verify(getRequestedFor(urlEqualTo("/api/v1/services")));
    }
}


 */