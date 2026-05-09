package com.eraytasay.wafflegateway.datasource;

import static org.mockito.ArgumentMatchers.any;

/*
@ExtendWith(MockitoExtension.class)
class ServiceDiscoveryServiceInstanceProviderTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private x provider;

    private final String TEST_URL = "http://discovery-service/api/services";

    @BeforeEach
    void setUp() {
        provider.setUrl(TEST_URL);
        provider.setRestClient(restClient);
    }

    @Test
    void fetchServicesSync_ShouldPopulateServicesMap_WhenResponseIsValid() {
        // --- GIVEN (Ön Koşullar) ---
        WaffleServiceInstance instance = new WaffleServiceInstance("auth-service", "1.1.1.1", 8085, "round-robin");
        Map<String, List<WaffleServiceInstance>> mockData = Map.of("auth-service", List.of(instance));
        
        Response<Map<String, List<WaffleServiceInstance>>> mockResponse = new Response<>();
        mockResponse.setData(mockData);

        // RestClient zincirleme yapısını (fluent API) mock'luyoruz
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(TEST_URL)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(mockResponse);

        // --- WHEN (Eylem) ---
        provider.fetchServicesSync();

        // --- THEN (Doğrulama) ---
        assertNotNull(provider.getServices("auth-service"));
        assertEquals(1, provider.getServices("auth-service").size());

        verify(restClient, times(1)).get();
    }

    @Test
    void fetchServicesSync_ShouldThrowException_WhenRestClientFails() {
        // --- GIVEN ---
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(TEST_URL)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        
        // RestClientException fırlatıldığında bizim sınıfın ServiceDiscoveryClientException fırlatmasını bekliyoruz
        when(responseSpec.body(any(ParameterizedTypeReference.class)))
                .thenThrow(new RestClientException("Connection Refused"));

        // --- WHEN & THEN ---
        assertThrows(ServiceDiscoveryClientException.class, () -> {
            provider.fetchServicesSync();
        });
    }

    @Test
    void fetchServicesSync_ShouldThrowException_WhenResponseIsNull() {
        // --- GIVEN ---
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(TEST_URL)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(null);

        // --- WHEN & THEN ---
        ServiceDiscoveryClientException exception = assertThrows(ServiceDiscoveryClientException.class, () -> {
            provider.fetchServicesSync();
        });

        assertTrue(exception.getMessage().contains("Response cannot be null"));
    }
}
*/