package com.eraytasay.wafflegateway.rpf.filter.chain;

import com.eraytasay.wafflegateway.rpf.core.RequestContext;
import com.eraytasay.wafflegateway.rpf.response.Response;
import org.springframework.stereotype.Component;

@Component
public class RequestForwarder implements IRequestForwarder {
    private final RequestProvider m_provider;
    private final RequestSender m_sender;

    public RequestForwarder(RequestProvider provider, RequestSender sender)
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
