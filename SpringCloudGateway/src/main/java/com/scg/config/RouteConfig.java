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
                .route("ms1", r -> r.path("/ms1/**")
                        .uri("lb://MS1MainServer"))
                .route("ms1-actuator", r -> r.path("/ms1/actuator/**")
                        .uri("lb://MS1MainServer/ms1/actuator"))
                
                .route("ms2", r -> r.path("/ms2/**")
                        .uri("lb://MS2MatchServer"))
                .route("ms2-actuator", r -> r.path("/ms2/actuator/**")
                        .uri("lb://MS2MatchServer/ms2/actuator"))
                
                .route("ms3", r -> r.path("/ms3/**")
                        .uri("lb://MS3LogServer"))
                .route("ms3-actuator", r -> r.path("/ms3/actuator/**")
                        .uri("lb://MS3LogServer/ms3/actuator"))
                
                .route("ms4", r -> r.path("/ms4/**")
                        .uri("lb://MS4LogServer"))
                .route("ms4-actuator", r -> r.path("/ms4/actuator/**")
                        .uri("lb://MS4LogServer/ms4/actuator"))
                
                .build();
    }
}
