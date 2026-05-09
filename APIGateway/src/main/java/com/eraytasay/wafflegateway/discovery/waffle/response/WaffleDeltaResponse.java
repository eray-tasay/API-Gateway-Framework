package com.eraytasay.wafflegateway.discovery.waffle.response;

import com.eraytasay.wafflegateway.discovery.waffle.response.body.WaffleDeltaResponseData;

public class WaffleDeltaResponse {
    private String m_message;
    private ResponseType m_type;
    private WaffleDeltaResponseData m_data;
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

    public WaffleDeltaResponseData getData()
    {
        return m_data;
    }

    public void setData(WaffleDeltaResponseData data)
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