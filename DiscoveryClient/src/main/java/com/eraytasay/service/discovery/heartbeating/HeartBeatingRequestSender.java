package com.eraytasay.service.discovery.heartbeating;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.eraytasay.service.discovery.dto.response.ResponseDto;
import com.eraytasay.service.discovery.heartbeating.dto.HeartBeatingRequestDto;
import com.eraytasay.service.discovery.exception.HeartBeatingException;

public class HeartBeatingRequestSender {
    private static final Logger log = LoggerFactory.getLogger(HeartBeatingRequestSender.class);

    private final String m_serviceDiscoveryUrl;
    private final String m_serviceName;
    private final int m_servicePort;
    private final RestClient m_restClient;
    private final String m_listenPath;

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder {
        private String m_serviceDiscoveryUrl;
        private String m_serviceName;
        private int m_servicePort;
        private RestClient m_restClient;
        private String m_listenPath;

        private Builder()
        {}

        public Builder serviceDiscoveryUrl(String serviceDiscoveryUrl)
        {
            m_serviceDiscoveryUrl = serviceDiscoveryUrl;
            return this;
        }

        public Builder serviceName(String serviceName)
        {
            m_serviceName = serviceName;
            return this;
        }

        public Builder servicePort(int servicePort)
        {
            m_servicePort = servicePort;
            return this;
        }

        public Builder restClient(RestClient restClient)
        {
            m_restClient = restClient;
            return this;
        }

        public Builder listenPath(String listenPath)
        {
            m_listenPath = listenPath;
            return this;
        }

        public HeartBeatingRequestSender build()
        {
            if (m_serviceDiscoveryUrl == null)
                throw new IllegalStateException("Service discovery URL cannot be null");
            if (m_serviceName == null)
                throw new IllegalStateException("Service name cannot be null");
            if (m_listenPath == null)
                throw new IllegalStateException("Listen path cannot be null");
            if (m_restClient == null)
                throw new IllegalStateException("RestClient cannot be null");
            if (m_servicePort <= 0 || m_servicePort > 65535)
                throw new IllegalStateException("Port must be between 1 and 65535");

            return new HeartBeatingRequestSender(this);
        }
    }

    private HeartBeatingRequestSender(Builder builder)
    {
        m_serviceDiscoveryUrl = builder.m_serviceDiscoveryUrl;
        m_serviceName = builder.m_serviceName;
        m_servicePort = builder.m_servicePort;
        m_restClient = builder.m_restClient;
        m_listenPath = builder.m_listenPath;
    }

    public String getServiceDiscoveryUrl()
    {
        return m_serviceDiscoveryUrl;
    }

    public String getServiceName()
    {
        return m_serviceName;
    }

    public int getServicePort()
    {
        return m_servicePort;
    }

    public RestClient getRestClient()
    {
        return m_restClient;
    }

    public String getListenPath()
    {
        return m_listenPath;
    }

    public String getFullUrl()
    {
        return "%s%s".formatted(m_serviceDiscoveryUrl, m_listenPath);
    }

    public ResponseDto<?> send()
    {
        var body = new HeartBeatingRequestDto();
        var fullUrl = getFullUrl();

        body.setServiceName(m_serviceName);
        body.setPort(m_servicePort);

        ResponseDto<?> response;

        try {
            response = m_restClient.post()
                    .uri(fullUrl)
                    .body(body)
                    .retrieve()
                    .body(ResponseDto.class);
        }
        catch (RestClientException ex) {
            throw new HeartBeatingException("Heart beating request cannot be sent.", ex);
        }

        log.info("Heart beating request is sent.");

        return response;
    }
}
