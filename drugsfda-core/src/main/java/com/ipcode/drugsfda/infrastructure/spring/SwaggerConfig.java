package com.ipcode.drugsfda.infrastructure.spring;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SecurityScheme(name = SwaggerConfig.ACCESS_TOKEN_AUTHORIZATION, type = SecuritySchemeType.HTTP, scheme = "bearer")
@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class SwaggerConfig {

  public static final String ACCESS_TOKEN_AUTHORIZATION = "BEARER_AUTHORIZATION";

  private final BuildProperties buildProperties;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title(buildProperties.getName() + " REST API")
            .description("Rest api for " + buildProperties.getName() + "." + buildProperties.getVersion())
            .contact(new Contact()
                .name("IPCODE")
                .url("www.ipcode.com")
            )
        );
  }

}
