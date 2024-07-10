package com.scg.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRoute {
	
    private final JwtAuthenticationFilter jwtAuthFilter;

    public CustomRoute(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }
    
	@Bean
    public RouteLocator ms1Route(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("ms1", r -> r.path("/ms1/**")
                        .uri("http://localhost:9999"))
                .route("ms2", r -> r.path("/ms2/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("http://localhost:9998"))
                .route("ms3", r -> r.path("/ms3/**")
                        .uri("http://localhost:9997"))
                .build();
    }
}
