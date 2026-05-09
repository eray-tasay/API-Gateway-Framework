package com.eraytasay.wafflegateway.rpf.request.param;

import java.util.*;

public final class QueryParameters implements IQueryParameters {
    private final Map<String, QueryParameter> m_parameters;

    private QueryParameters()
    {
        m_parameters = new HashMap<>();
    }

    public static QueryParameters of()
    {
        return new QueryParameters();
    }

    public static QueryParameters of(IQueryParameters queryParameters)
    {
        var object = new QueryParameters();

        queryParameters.getNames().forEach(name -> {
            var param = queryParameters.get(name);

            object.m_parameters.put(name, QueryParameter.of(param));
        });

        return object;
    }

    @Override
    public IQueryParameter get(String name)
    {
        return m_parameters.get(name);
    }

    @Override
    public int count()
    {
        return m_parameters.size();
    }

    @Override
    public boolean isEmpty()
    {
        return m_parameters.isEmpty();
    }

    @Override
    public Iterable<String> getNames()
    {
        return new ArrayList<>(m_parameters.keySet());
    }

    public void clear()
    {
        m_parameters.clear();
    }

    public ParameterMutator mutate(String name)
    {
        return new ParameterMutator(name);
    }

    public final class ParameterMutator {
        private final String m_name;

        private ParameterMutator(String name)
        {
            m_name = Objects.requireNonNull(name);
        }

        public ParameterMutator addLast(String value)
        {
            var param = m_parameters.get(m_name);

            if (param == null) {
                param = QueryParameter.of(m_name);
                m_parameters.put(m_name, param);
            }

            param.addLast(value);
            return this;
        }

        public ParameterMutator removeLast()
        {
            var param = m_parameters.get(m_name);

            if (param == null)
                throw new NoSuchElementException("There is no query parameter with name %s".formatted(m_name));

            param.removeLast();

            if (param.isEmpty())
                m_parameters.remove(m_name);

            return this;
        }

        public ParameterMutator clear()
        {
            var param = m_parameters.get(m_name);

            if (param == null)
                throw new NoSuchElementException("There is no query parameter with name %s".formatted(m_name));

            param.clear();
            m_parameters.remove(m_name);

            return this;
        }
    }
}
