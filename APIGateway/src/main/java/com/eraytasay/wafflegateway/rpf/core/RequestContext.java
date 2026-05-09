package com.eraytasay.wafflegateway.rpf.core;

import com.eraytasay.wafflegateway.rpf.request.*;
import com.eraytasay.wafflegateway.rpf.response.Response;
import com.eraytasay.wafflegateway.rpf.route.IRoute;

public class RequestContext {
    private final IRequest m_incomingRequest;
    private final Request m_exchangeRequest;
    private final IRoute m_route;
    private Response m_response;

    public RequestContext(IRequest incomingRequest, IRoute route)
    {
        m_incomingRequest = Request.of(incomingRequest);
        m_exchangeRequest = Request.of(incomingRequest);
        m_route = route;
    }

    public Request getExchangeRequest()
    {
        return m_exchangeRequest;
    }

    public IRequest getIncomingRequest()
    {
        return m_incomingRequest;
    }

    public IRoute getRoute()
    {
        return m_route;
    }

    public Response getResponse()
    {
        return m_response;
    }

    public void setResponse(Response response)
    {
        m_response = response;
    }
}
