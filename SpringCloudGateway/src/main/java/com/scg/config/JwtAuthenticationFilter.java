package com.scg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Value("${jwt.secret}")
    private String secretKey;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {
        // Put the configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "No Bearer token", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token)
                        .getBody();

                // JWT가 유효한 경우, 필요한 추가 처리를 여기에 수행

            } catch (Exception e) {
                return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }
}
