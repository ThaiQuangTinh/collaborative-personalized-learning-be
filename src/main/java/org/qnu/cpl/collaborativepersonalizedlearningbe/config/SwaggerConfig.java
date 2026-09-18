package org.qnu.cpl.collaborativepersonalizedlearningbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Collaborative personalized learning")
                        .version("1.0")
                        .description("Spring Boot 3 + Swagger UI"));
    }

    // http://localhost:8082/swagger-ui/index.html
}
