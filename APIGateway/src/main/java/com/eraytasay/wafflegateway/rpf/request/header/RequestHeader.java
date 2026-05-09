package com.eraytasay.wafflegateway.rpf.request.header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public final class RequestHeader implements IRequestHeader {
    private final String m_name;
    private final List<String> m_values;

    private RequestHeader(String name)
    {
        m_name = name;
        m_values = new ArrayList<>();
    }

    public static RequestHeader of(IRequestHeader requestHeader)
    {
        return of(requestHeader.getName(), requestHeader.getAll());
    }

    public static RequestHeader of(String name)
    {
        return new RequestHeader(name);
    }

    public static RequestHeader of(String name, Iterable<String> values)
    {
        var object = new RequestHeader(name);

        values.forEach(object.m_values::add);

        return object;
    }

    public static RequestHeader of(String name, String... values)
    {
        return of(name, Arrays.asList(values));
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
