package com.eraytasay.service.discovery.unregister;

import com.eraytasay.service.discovery.dto.response.ResponseDto;
import com.eraytasay.service.discovery.unregister.dto.UnregisterServiceDto;
import com.eraytasay.service.discovery.exception.ServiceRegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

public class ServiceUnregistrar {
    private static final Logger log = LoggerFactory.getLogger(ServiceUnregistrar.class);

    private final String m_serviceDiscoveryUrl;
    private final String m_serviceName;
    private final String m_unregisterPath;
    private final int m_servicePort;
    private final RestClient m_restClient;

    private ServiceUnregistrar(ServiceUnregistrar.Builder builder)
    {
        m_serviceDiscoveryUrl = builder.m_serviceDiscoveryUrl;
        m_serviceName = builder.m_serviceName;
        m_unregisterPath = builder.m_unregisterPath;
        m_servicePort = builder.m_servicePort;
        m_restClient = builder.m_restClient;
    }

    public static ServiceUnregistrar.Builder builder()
    {
        return new ServiceUnregistrar.Builder();
    }

    public static class Builder {
        private String m_serviceDiscoveryUrl;
        private String m_serviceName;
        private String m_unregisterPath;
        private int m_servicePort;
        private RestClient m_restClient;

        public ServiceUnregistrar.Builder serviceDiscoveryUrl(String url)
        {
            m_serviceDiscoveryUrl = url;
            return this;
        }

        public ServiceUnregistrar.Builder serviceName(String name)
        {
            m_serviceName = name;
            return this;
        }

        public ServiceUnregistrar.Builder unregisterPath(String path)
        {
            m_unregisterPath = path;
            return this;
        }

        public ServiceUnregistrar.Builder servicePort(int port)
        {
            m_servicePort = port;
            return this;
        }

        public ServiceUnregistrar.Builder restClient(RestClient restClient)
        {
            m_restClient = restClient;
            return this;
        }

        public ServiceUnregistrar build()
        {
            if (m_serviceDiscoveryUrl == null)
                throw new IllegalStateException("Service discovery URL cannot be null");
            if (m_serviceName == null)
                throw new IllegalStateException("Service name cannot be null");
            if (m_unregisterPath == null)
                throw new IllegalStateException("Unregister path cannot be null");
            if (m_servicePort <= 0 || m_servicePort > 65535)
                throw new IllegalStateException("Port must be between 1 and 65535");
            if (m_restClient == null)
                throw new IllegalStateException("RestClient cannot be null");

            return new ServiceUnregistrar(this);
        }
    }

    public String getServiceDiscoveryUrl()
    {
        return m_serviceDiscoveryUrl;
    }

    public String getServiceName()
    {
        return m_serviceName;
    }

    public String getUnregisterPath()
    {
        return m_unregisterPath;
    }

    public int getServicePort()
    {
        return m_servicePort;
    }

    public RestClient getRestClient()
    {
        return m_restClient;
    }

    public String getFullUrl()
    {
        return "%s%s".formatted(m_serviceDiscoveryUrl, m_unregisterPath);
    }

    public ResponseDto<?> unregister()
    {
        var body = new UnregisterServiceDto();
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
            throw new ServiceRegistrationException("Service %s cannot be unregistered with %s.".formatted(m_serviceName, fullUrl), ex);
        }

        log.info("Service {} is unregistered with {}.", m_serviceName, fullUrl);

        return response;
    }
}
