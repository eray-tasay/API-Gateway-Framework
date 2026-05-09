package com.eraytasay.service.discovery.dto.response;

public class ResponseDto<T> {
    private String m_message;
    private ResponseType m_type;
    private T m_data;
    private long m_timestamp;

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