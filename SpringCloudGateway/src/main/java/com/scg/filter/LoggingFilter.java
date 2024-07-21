package com.scg.filter;

import com.scg.model.RequestLog;
import com.scg.repository.RequestLogRepository;
import com.scg.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@Order(1)
public class LoggingFilter implements GlobalFilter, GatewayFilter {

    @Autowired
    private RequestLogRepository requestLogRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String endpoint = exchange.getRequest().getPath().toString();
        LocalDateTime timestamp = LocalDateTime.now();

        System.out.println("loggingFilter");
        
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Integer statusCodeValue = exchange.getResponse().getStatusCode().value();
            HttpStatus statusCode = HttpStatus.resolve(statusCodeValue);
            boolean success = (statusCode != null && statusCode.is2xxSuccessful());

            RequestLog log = new RequestLog();
            log.setId(sequenceGeneratorService.generateSequence(RequestLog.SEQUENCE_NAME));
            log.setEndpoint(endpoint);
            log.setTimestamp(timestamp);
            log.setResponseCode(statusCode != null ? statusCode.value() : 500);
            log.setSuccess(success);

            requestLogRepository.save(log);
        }));
    }
}
