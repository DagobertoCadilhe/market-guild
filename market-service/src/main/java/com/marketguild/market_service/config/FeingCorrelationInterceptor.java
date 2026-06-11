package com.marketguild.market_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeingCorrelationInterceptor implements RequestInterceptor {
    public FeingCorrelationInterceptor() {
        super();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            String correlationId = attributes.getRequest()
                    .getHeader("X-Correlation-ID");
            if (correlationId != null) {
                requestTemplate.header("X-Correlation-ID", correlationId);
            }
        }
    }
}
