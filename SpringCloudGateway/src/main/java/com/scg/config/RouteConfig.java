package com.scg.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator ms1Route(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("ms1", r -> {
                    return r.path("/ms1/**")
                            .uri("http://localhost:9999");
                })
                .route("ms2", r -> {
                    return r.path("/ms2/**")
                            .uri("http://localhost:9998");
                })
                .route("ms3", r -> {
                    return r.path("/ms3/**")
                            .uri("http://localhost:9997");
                })
                .route("ms4", r -> {
                    return r.path("/ms4/**")
                            .uri("http://localhost:9996");
                })
                .build();
    }
}
