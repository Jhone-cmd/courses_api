package br.com.jhonecmd.courses_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

        @Bean
        OpenAPI openAPI() {
                return new OpenAPI()
                                .info(new Info().title("Courses API")
                                                .description(
                                                                "This API allows for complete management of a course catalog. Through it, you can register new training courses, toggle their availability (active), and perform refined searches by name or category. Ideal for LMS (Learning Management Systems).")
                                                .version("1.0"))
                                .schemaRequirement("auth", createSecurityScheme());
        }

        private SecurityScheme createSecurityScheme() {
                return new SecurityScheme().name("auth").type(SecurityScheme.Type.HTTP).scheme("bearer")
                                .bearerFormat("JWT");
        }
}
