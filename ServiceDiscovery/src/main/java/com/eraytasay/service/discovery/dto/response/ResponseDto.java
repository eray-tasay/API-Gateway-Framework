package com.eraytasay.service.discovery.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDto<T> {
    private String m_message;
    private ResponseType m_type;
    private T m_data;
    private long m_timestamp;

    public ResponseDto(String message, ResponseType type, T data, long timestamp)
    {
        this.m_message = message;
        this.m_type = type;
        this.m_data = data;
        this.m_timestamp = timestamp;
    }

    public ResponseDto(String message, ResponseType type, T data)
    {
        this(message, type, data, System.currentTimeMillis());
    }

    public ResponseDto(ResponseType type, T data)
    {
        this(null, type, data);
    }

    public ResponseDto(String message, ResponseType type)
    {
        this(message, type, null);
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public String getMessage()
    {
        return m_message;
    }

    public void setMessage(String message)
    {
        m_message = message;
    }

    public ResponseType getType()
    {
        return m_type;
    }

    public void setType(ResponseType type)
    {
        m_type = type;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T getData()
    {
        return m_data;
    }

    public void setData(T data)
    {
        m_data = data;
    }

    public long getTimestamp()
    {
        return m_timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        m_timestamp = timestamp;
    }
}