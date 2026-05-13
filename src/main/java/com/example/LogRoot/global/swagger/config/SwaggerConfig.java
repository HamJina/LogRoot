package com.example.LogRoot.global.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // 1. 스웨거 전역에서 사용할 보안 스키마 정의 (Bearer Token 방식)
        String securityKey = "BearerAuth";
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securityKey);

        return new OpenAPI()
                .info(new Info()
                        .title("LogRoot API")
                        .description("LogRoot API 명세서입니다.")
                        .version("v1.0.0")
                )
                .components(new Components().addSecuritySchemes(securityKey, securityScheme))
                .security(List.of(securityRequirement));
    }
}