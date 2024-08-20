package com.scg.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator Route(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms1", r -> r.path("/ms1/**")
                        .uri("http://localhost:30114"))
                .route("ms1-actuator", r -> r.path("/ms1/actuator/**")
                        .uri("http://localhost:30114/ms1/actuator"))
                
                .route("ms2", r -> r.path("/ms2/**")
                        .uri("http://localhost:30115"))
                .route("ms2-actuator", r -> r.path("/ms2/actuator/**")
                        .uri("http://localhost:30115/ms2/actuator"))
                
                .route("ms3", r -> r.path("/ms3/**")
                        .uri("http://localhost:30116"))
                .route("ms3-actuator", r -> r.path("/ms3/actuator/**")
                        .uri("http://localhost:30116/ms3/actuator"))
                
                .build();
    }
}
