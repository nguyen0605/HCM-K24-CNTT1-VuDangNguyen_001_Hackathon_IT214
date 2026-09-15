package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("category-service", r -> r
                        .path("/api/categories/**", "/api/category/**", "/categories/**", "/category/**")
                        .uri("lb://category-service")
                )
                .route("product-service", r -> r
                        .path("/api/products/**", "/api/product/**", "/products/**", "/product/**")
                        .uri("lb://product-service")
                )
                .build();
    }
}