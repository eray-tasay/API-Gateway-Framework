package com.eraytasay.wafflegateway.rpf.request.header;

import java.util.*;

public final class RequestHeaders implements IRequestHeaders {
    private final Map<String, RequestHeader> m_headers;

    private RequestHeaders()
    {
        m_headers = new HashMap<>();
    }

    public static RequestHeaders of()
    {
        return new RequestHeaders();
    }

    public static RequestHeaders of(IRequestHeaders requestHeaders)
    {
        var object = new RequestHeaders();

        requestHeaders.getNames().forEach(name -> {
            var header = requestHeaders.get(name);

            object.m_headers.put(name, RequestHeader.of(header));
        });

        return object;
    }

    @Override
    public IRequestHeader get(String name)
    {
        return m_headers.get(name);
    }

    @Override
    public int count()
    {
        return m_headers.size();
    }

    @Override
    public boolean isEmpty()
    {
        return m_headers.isEmpty();
    }

    @Override
    public Iterable<String> getNames()
    {
        return new ArrayList<>(m_headers.keySet());
    }

    public void clear()
    {
        m_headers.clear();
    }

    public HeaderMutator mutate(String name)
    {
        return new HeaderMutator(name);
    }

    public final class HeaderMutator {
        private final String m_name;

        private HeaderMutator(String name)
        {
            m_name = Objects.requireNonNull(name);
        }

        public HeaderMutator addLast(String value)
        {
            var header = m_headers.get(m_name);

            if (header == null) {
                header = RequestHeader.of(m_name);
                m_headers.put(m_name, header);
            }

            header.addLast(value);
            return this;
        }

        public HeaderMutator removeLast()
        {
            var header = m_headers.get(m_name);

            if (header == null)
                throw new NoSuchElementException("There is no header with name %s".formatted(m_name));

            header.removeLast();

            if (header.isEmpty())
                m_headers.remove(m_name);

            return this;
        }

        public HeaderMutator clear()
        {
            var header = m_headers.get(m_name);

            if (header == null)
                throw new NoSuchElementException("There is no header with name %s".formatted(m_name));

            header.clear();
            m_headers.remove(m_name);

            return this;
        }
    }
}
