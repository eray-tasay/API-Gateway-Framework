package com.eraytasay.wafflegateway.rpf.request;

import com.eraytasay.wafflegateway.exception.InvalidURIPathException;
import com.eraytasay.wafflegateway.rpf.request.header.RequestHeaders;
import com.eraytasay.wafflegateway.rpf.request.param.QueryParameters;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.net.URISyntaxException;

public final class Request implements IRequest {
    private HttpMethod m_method;
    private String m_host;
    private String m_path;
    private int m_port;
    private String m_scheme;
    private RequestHeaders m_headers;
    private QueryParameters m_queryParameters;
    private byte[] m_body;

    private Request()
    {}

    public static Request of(IRequest request)
    {
        var object = new Request();

        object.setMethod(request.getMethod());
        object.setHost(request.getHost());
        object.setPath(request.getPath());
        object.setPort(request.getPort());
        object.setScheme(request.getScheme());
        object.setHeaders(RequestHeaders.of(request.getHeaders()));
        object.setQueryParameters(QueryParameters.of(request.getQueryParameters()));
        object.setBody(request.getBody().clone());

        return object;
    }

    @Override
    public HttpMethod getMethod()
    {
        return m_method;
    }

    public void setMethod(HttpMethod method)
    {
        m_method = method;
    }

    @Override
    public String getPath()
    {
        return m_path;
    }

    public void setPath(String path)
    {
        checkValidHttpPath(path);
        m_path = path;
    }

    @Override
    public RequestHeaders getHeaders()
    {
        return m_headers;
    }

    public void setHeaders(RequestHeaders headers)
    {
        m_headers = headers;
    }

    @Override
    public QueryParameters getQueryParameters()
    {
        return m_queryParameters;
    }

    public void setQueryParameters(QueryParameters queryParameters)
    {
        m_queryParameters = queryParameters;
    }

    @Override
    public byte[] getBody()
    {
        return m_body;
    }

    public void setBody(byte[] body)
    {
        m_body = body;
    }

    @Override
    public int getPort()
    {
        return m_port;
    }

    public void setPort(int port)
    {
        m_port = port;
    }

    @Override
    public String getScheme()
    {
        return m_scheme;
    }

    public void setScheme(String scheme)
    {
        m_scheme = scheme;
    }

    @Override
    public String getHost()
    {
        return m_host;
    }

    public void setHost(String host)
    {
        m_host = host;
    }

    private Request(Builder builder)
    {
        setMethod(builder.m_method);
        setPath(builder.m_path);
        setHeaders(builder.m_headers);
        setQueryParameters(builder.m_queryParameters);
        setBody(builder.m_body);
        setHost(builder.m_host);
        setPort(builder.m_port);
        setScheme(builder.m_scheme);
    }

    public static class Builder {
        private HttpMethod m_method;
        private String m_host;
        private int m_port;
        private String m_scheme;
        private String m_path;
        private RequestHeaders m_headers;
        private QueryParameters m_queryParameters;
        private byte[] m_body;

        public Builder headers(RequestHeaders headers)
        {
            m_headers = headers;
            return this;
        }

        public Builder queryParameters(QueryParameters queryParameters)
        {
            m_queryParameters = queryParameters;
            return this;
        }

        public Builder body(byte[] body)
        {
            m_body = body;
            return this;
        }

        public Builder path(String path)
        {
            m_path = path;
            return this;
        }

        public Builder scheme(String scheme)
        {
            m_scheme = scheme;
            return this;
        }

        public Builder host(String host)
        {
            m_host = host;
            return this;
        }

        public Builder port(int port)
        {
            m_port = port;
            return this;
        }

        public Builder method(HttpMethod method)
        {
            m_method = method;
            return this;
        }

        public Request build()
        {
            if (m_path == null || m_method == null)
                throw new IllegalArgumentException("path and method must not be null");

            return new Request(this);
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }

    private static boolean isValidHttpPath(String path)
    {
        if (path == null || !path.startsWith("/"))
            return false;

        try {
            new URI(null, null, path, null, null);

            return true;
        }
        catch (URISyntaxException e) {
            return false;
        }
    }

    private static void checkValidHttpPath(String path)
    {
        if (!isValidHttpPath(path))
            throw new InvalidURIPathException("Invalid path: %s".formatted(path));
    }
}
