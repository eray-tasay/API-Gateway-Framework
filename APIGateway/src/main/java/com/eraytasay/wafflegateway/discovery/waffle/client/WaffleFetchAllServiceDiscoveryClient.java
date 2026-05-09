package com.eraytasay.wafflegateway.discovery.waffle.client;

import com.eraytasay.wafflegateway.discovery.client.IFetchAllServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.waffle.response.WaffleFetchAllResponse;
import com.eraytasay.wafflegateway.exception.ServiceDiscoveryClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

public class WaffleFetchAllServiceDiscoveryClient implements IFetchAllServiceDiscoveryClient<WaffleFetchAllResponse> {
    private static final Logger log = LoggerFactory.getLogger(WaffleFetchAllServiceDiscoveryClient.class);

    private RestClient m_restClient;
    private String m_url;

    public RestClient getRestClient()
    {
        return m_restClient;
    }

    public void setRestClient(RestClient restClient)
    {
        m_restClient = restClient;
    }

    public String getUrl()
    {
        return m_url;
    }

    public void setUrl(String url)
    {
        m_url = url;
    }

    @Override
    public ResponseEntity<WaffleFetchAllResponse> fetchAll()
    {
        ResponseEntity<WaffleFetchAllResponse> response;

        try {
            response = m_restClient.get()
                    .uri(m_url)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<>() {});
        }
        catch (RestClientException ex) {
            throw new ServiceDiscoveryClientException(ex);
        }

        log.info("Services are fetched from service discovery.");

        return response;
    }
}
