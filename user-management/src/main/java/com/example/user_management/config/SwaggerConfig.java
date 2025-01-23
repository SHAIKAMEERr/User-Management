package com.example.user_management.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        log.info("Configuring Swagger OpenAPI");
        return new OpenAPI()
                .info(new Info()
                        .title("User Management Service API")
                        .version("1.0")
                        .description("API documentation for the User Management Service")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@example.com")
                        ))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Local server")
                ));
    }
}
