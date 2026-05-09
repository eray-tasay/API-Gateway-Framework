package com.eraytasay.wafflegateway.rpf.route;

import com.eraytasay.wafflegateway.config.prop.GatewayProperties;
import com.eraytasay.wafflegateway.rpf.filter.IRequestFilter;
import com.eraytasay.wafflegateway.rpf.filter.parser.RequestFilterParser;
import com.eraytasay.wafflegateway.rpf.predicate.parser.RequestPredicateParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class RouteDefinitionRouteProvider implements IRouteProvider {
    private final List<RouteDefinition> m_definitions;
    private final RequestPredicateParser m_predicateParser;
    private final RequestFilterParser m_filterParser;

    public RouteDefinitionRouteProvider(GatewayProperties properties,
                                        RequestPredicateParser predicateParser,
                                        RequestFilterParser filterParser)
    {
        m_definitions = List.copyOf(properties.getRoutes());
        m_predicateParser = predicateParser;
        m_filterParser = filterParser;
    }

    @Override
    public List<IRoute> getRoutes()
    {
        var routes = new ArrayList<IRoute>();

        for (RouteDefinition definition : m_definitions) {
            var matcherBuilder = RequestMatcher.builder();
            definition.getPredicates().forEach(predicateDefinition -> matcherBuilder.predicate(m_predicateParser.parse(predicateDefinition)));

            var filters = new ArrayList<IRequestFilter>();
            definition.getFilters().forEach(filterDefinition -> filters.add(m_filterParser.parse(filterDefinition)));

            routes.add(new Route(definition.getId(), definition.getUri(), matcherBuilder.build(), filters));
        }

        return routes;
    }
}
