package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.response.Response;

public class RequestForwarder implements IRequestForwarder {
    private final IRequestProvider m_provider;
    private final RequestSender m_sender;

    public RequestForwarder(IRequestProvider provider, RequestSender sender)
    {
        m_provider = provider;
        m_sender = sender;
    }

    @Override
    public Response forward(RequestContext context)
    {
        var request = m_provider.provide(context);

        return m_sender.sendRequest(request);
    }
}
