package com.ercanbeyen.gatewayserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI gatewayOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server()
                                .url("http://localhost:8080")
                                .description("Gateway Server API")
                        )
                )
                .info(new Info()
                        .title("Exam Management System")
                        .description("Exam Management System API")
                        .version("1.0")
                        .contact(new Contact().name("Support")
                                .email("support@email.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                );
    }
}
