package com.eraytasay.wafflegateway.rpf.predicate.factory;

import com.eraytasay.wafflegateway.rpf.predicate.MethodPredicate;
import com.eraytasay.wafflegateway.rpf.predicate.IRequestPredicate;
import org.springframework.http.HttpMethod;

import java.util.List;

public final class MethodPredicateFactory implements IRequestPredicateFactory {
    @Override
    public String name()
    {
        return "Method";
    }

    @Override
    public IRequestPredicate create(List<String> args)
    {
        if (args.size() != 1)
            throw new IllegalArgumentException("Method predicate requires exactly one argument");

        var method = HttpMethod.valueOf(args.getFirst());

        return new MethodPredicate(method);
    }
}