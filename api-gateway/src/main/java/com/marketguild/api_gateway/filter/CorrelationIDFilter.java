package com.marketguild.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
public class CorrelationIDFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(CorrelationIDFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {



        String correlationID = exchange.getRequest()
                .getHeaders()
                .getFirst("X-Correlation-ID");


        if (correlationID == null) {
            correlationID = UUID.randomUUID().toString();
        }

        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header("X-Correlation-ID", correlationID)
                .build();

        log.info("[{}] Requisição: {} {}", correlationID,
                exchange.getRequest().getMethod(),         // Salva se foi GET, POST, etc...
                exchange.getRequest().getURI().getPath()); // Salva o ENDPOINT

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
