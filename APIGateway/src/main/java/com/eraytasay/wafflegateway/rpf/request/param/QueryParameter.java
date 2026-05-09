package com.eraytasay.wafflegateway.rpf.request.param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public final class QueryParameter implements IQueryParameter {
    private final String m_name;
    private final List<String> m_values;

    private QueryParameter(String name)
    {
        m_name = name;
        m_values = new ArrayList<>();
    }

    public static QueryParameter of(IQueryParameter queryParameter)
    {
        return of(queryParameter.getName(), queryParameter.getAll());
    }

    public static QueryParameter of(String name, Iterable<String> values)
    {
        var object = new QueryParameter(name);

        values.forEach(object.m_values::add);

        return object;
    }

    public static QueryParameter of(String name, String... values)
    {
        return QueryParameter.of(name, Arrays.asList(values));
    }

    public void addLast(String value)
    {
        m_values.add(value);
    }

    public void removeLast()
    {
        if (m_values.isEmpty())
            throw new NoSuchElementException("There is no value to remove. Header is empty.");

        m_values.removeLast();
    }

    public void clear()
    {
        m_values.clear();
    }

    @Override
    public String getFirst()
    {
        return m_values.isEmpty() ? null : m_values.getFirst();
    }

    @Override
    public String getLast()
    {
        return m_values.isEmpty() ? null : m_values.getLast();
    }

    @Override
    public Iterable<String> getAll()
    {
        return new ArrayList<>(m_values);
    }

    @Override
    public String getName()
    {
        return m_name;
    }

    @Override
    public int count()
    {
        return m_values.size();
    }

    @Override
    public boolean isEmpty()
    {
        return m_values.isEmpty();
    }
}
