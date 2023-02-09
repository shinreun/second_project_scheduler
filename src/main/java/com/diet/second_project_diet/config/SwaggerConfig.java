package com.diet.second_project_diet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI floOpenAPI() {
    // version, title, description은 마음대로 설정
    Info info = new Info().version("0.0.1").title("Diet Service API").description("Diet Service Restful API 명세서");
    return new OpenAPI().info(info);
  }
}
