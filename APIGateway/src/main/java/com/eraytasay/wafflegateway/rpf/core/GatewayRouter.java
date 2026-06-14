package com.eraytasay.wafflegateway.rpf.core;

import com.eraytasay.wafflegateway.rpf.request.RequestMapper;
import com.eraytasay.wafflegateway.rpf.response.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GatewayRouter {
    private final ResponseWriter m_responseWriter;
    private final RequestMapper m_requestMapper;
    private final GatewayHandler m_gatewayHandler;

    public GatewayRouter(ResponseWriter responseWriter, RequestMapper requestMapper, GatewayHandler gatewayHandler)
    {
        m_responseWriter = responseWriter;
        m_requestMapper = requestMapper;
        m_gatewayHandler = gatewayHandler;
    }

    public void route(HttpServletRequest httpServletRequest,  HttpServletResponse httpServletResponse)
    {
        var response = m_gatewayHandler.handle(m_requestMapper.map(httpServletRequest));

        m_responseWriter.write(httpServletResponse, response);
    }
}
