package com.example.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service",
                        r -> r.path("/users/**")
                              .uri("lb://USER-SERVICE"))
                .route("train-service",
                        r -> r.path("/trains/**", "/booking/**")
                              .uri("lb://TRAIN-SERVICE"))
                .route("ticket-service",
                        r -> r.path("/tickets/**")
                              .uri("lb://TICKET-SERVICE"))
                .route("payment-service",
                        r -> r.path("/payments/**")
                              .uri("lb://PAYMENT-SERVICE"))
                .build();
    }
}