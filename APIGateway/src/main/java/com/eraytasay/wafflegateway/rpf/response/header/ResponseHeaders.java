package com.eraytasay.wafflegateway.rpf.response.header;

import java.util.*;

public final class ResponseHeaders implements IResponseHeaders {
    private final Map<String, ResponseHeader> m_headers;

    public ResponseHeaders()
    {
        m_headers = new HashMap<>();
    }

    @Override
    public IResponseHeader get(String name)
    {
        return m_headers.get(name);
    }

    @Override
    public Iterable<String> getNames()
    {
        return new ArrayList<>(m_headers.keySet());
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
                header = new ResponseHeader(m_name);
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
