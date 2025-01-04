package com.ercanbeyen.gatewayserver.filter;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
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
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (Optional.ofNullable(authHeader).isPresent() && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    restTemplate.getForObject("http://localhost:9898/auth/validate?token=" + authHeader, String.class);
                    Boolean checkRoleResponse = restTemplate.getForObject("http://localhost:9898/auth/check-role?role={role}&token={token}", Boolean.class, config.getRole(), authHeader);
                    assert checkRoleResponse != null;

                    if (!checkRoleResponse) {
                        var response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return response.setComplete();
                    }

                    String username = restTemplate.getForObject("http://localhost:9898/auth/extract/username?token=" + authHeader, String.class);
                    serverHttpRequest = exchange.getRequest()
                            .mutate()
                            .header("username", username)
                            .build();
                } catch (Exception exception) {
                    log.error("RestTemplate did not send the request. Exception: {}", exception.getMessage());
                    throw new RuntimeException("Unauthorized access. Exception: " + exception.getMessage());
                }
            }

            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        });
    }

    @Data
    public static class Config {
        private String role;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("role");
    }
}
