package com.ercanbeyen.gatewayserver.filter;

import com.ercanbeyen.gatewayserver.exception.UnauthorizedUserException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final String AUTH_URL = "http://localhost:9898/auth";
    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate restTemplate;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = null;
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) { // header contains token or not
                    throw new UnauthorizedUserException("Missing authorization header");
                }

                String authHeader = Objects.requireNonNull(
                        exchange.getRequest()
                                .getHeaders()
                                .get(HttpHeaders.AUTHORIZATION))
                        .get(0);

                if (Optional.ofNullable(authHeader).isPresent() && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    restTemplate.getForObject(AUTH_URL + "/validate?token=" + authHeader, String.class);
                    Boolean hasRole = restTemplate.getForObject(AUTH_URL + "/check-role?role={role}&token={token}", Boolean.class, config.getRole(), authHeader);
                    assert hasRole != null;

                    if (!hasRole) {
                        log.error("Access denied for user");
                        return constructResponse(exchange, HttpStatus.FORBIDDEN);
                    }

                    String username = restTemplate.getForObject(AUTH_URL + "/extract/username?token=" + authHeader, String.class);
                    assert username != null;

                    serverHttpRequest = exchange.getRequest()
                            .mutate()
                            .header("loggedInUser", username)
                            .build();
                } catch (Exception exception) {
                    log.error("AuthenticationFilter::apply exception caught: {}", exception.getMessage());
                    return constructResponse(exchange, HttpStatus.UNAUTHORIZED);
                }
            }

            assert serverHttpRequest != null;
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        });
    }

    @Data
    public static class Config {
        private String role;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("role");
    }

    private static Mono<Void> constructResponse(ServerWebExchange exchange, HttpStatus status) {
        var response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }
}
