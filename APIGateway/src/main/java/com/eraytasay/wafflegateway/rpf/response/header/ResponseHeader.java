package com.eraytasay.wafflegateway.rpf.response.header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public final class ResponseHeader implements IResponseHeader {
    private final String m_name;
    private final List<String> m_values;

    public ResponseHeader(String name)
    {
        m_name = name;
        m_values = new ArrayList<>();
    }

    public ResponseHeader(String name, Iterable<String> values)
    {
        this(name);
        values.forEach(m_values::add);
    }

    public ResponseHeader(String name, String... values)
    {
        this(name, Arrays.asList(values));
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
}
