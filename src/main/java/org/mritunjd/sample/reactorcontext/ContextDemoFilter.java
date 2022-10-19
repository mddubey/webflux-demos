package org.mritunjd.sample.reactorcontext;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ContextDemoFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String id = exchange.getRequest().getQueryParams().get("id").get(0);
        //Change after upgrading to latest spring boot version
        //Few methods are deprceted/removes
        return chain.filter(exchange).subscriberContext(ctx -> {
            ctx = ctx.put("IP Address", id + ".123.23.43");
            ctx = ctx.put("Location", id + "-Street");
            return ctx;
        });
    }
}