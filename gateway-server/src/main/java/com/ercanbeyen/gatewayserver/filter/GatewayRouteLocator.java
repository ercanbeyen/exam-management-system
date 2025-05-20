package com.ercanbeyen.gatewayserver.filter;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayRouteLocator {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/SCHOOL-SERVICE/v3/api-docs").and().method(HttpMethod.GET).uri("lb://SCHOOL-SERVICE"))
                .route(r -> r.path("/CANDIDATE-SERVICE/v3/api-docs").and().method(HttpMethod.GET).uri("lb://CANDIDATE-SERVICE"))
                .route(r -> r.path("/AUTH-SERVICE/v3/api-docs").and().method(HttpMethod.GET).uri("lb://AUTH-SERVICE"))
                .route(r -> r.path("/EXAM-SERVICE/v3/api-docs").and().method(HttpMethod.GET).uri("lb://EXAM-SERVICE"))
                .route(r -> r.path("/NOTIFICATION-SERVICE/v3/api-docs").and().method(HttpMethod.GET).uri("lb://NOTIFICATION-SERVICE"))
                .build();
    }
}
