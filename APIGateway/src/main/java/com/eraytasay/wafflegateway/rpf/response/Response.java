package com.eraytasay.wafflegateway.rpf.response;

import com.eraytasay.wafflegateway.rpf.response.header.ResponseHeaders;
import org.springframework.http.HttpStatus;

public class Response implements IResponse {
    private HttpStatus m_statusCode;
    private ResponseHeaders m_headers;
    private byte[] m_body;

    @Override
    public HttpStatus getStatusCode()
    {
        return m_statusCode;
    }

    public void setStatusCode(HttpStatus statusCode)
    {
        m_statusCode = statusCode;
    }

    @Override
    public ResponseHeaders getHeaders()
    {
        return m_headers;
    }

    public void setHeaders(ResponseHeaders headers)
    {
        m_headers = headers;
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

    private Response(Builder builder)
    {
        setStatusCode(builder.m_statusCode);
        setHeaders(builder.m_headers);
        setBody(builder.m_body);
    }

    public static class Builder {
        private HttpStatus m_statusCode;
        private ResponseHeaders m_headers;
        private byte[] m_body;

        private Builder()
        {
            m_headers = new ResponseHeaders();
            m_body = new byte[0];
        }

        public Builder headers(ResponseHeaders headers)
        {
            m_headers = headers;
            return this;
        }

        public Builder body(byte[] body)
        {
            m_body = body;
            return this;
        }

        public Builder statusCode(HttpStatus statusCode)
        {
            m_statusCode = statusCode;
            return this;
        }

        public Response build()
        {
            if (m_statusCode == null)
                throw new IllegalArgumentException("statusCode must not be null");

            return new Response(this);
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }
}
