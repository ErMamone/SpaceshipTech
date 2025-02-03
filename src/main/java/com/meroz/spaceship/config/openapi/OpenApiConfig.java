package com.meroz.spaceship.config.openapi;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfig {

	@Value("${spaceship.openapi.external}")
	private String external;

	@Value("${spaceship.openapi.internal}")
	private String internal;

	@Value("${spaceship.openapi.title}")
	private String title;

	@Value("${spaceship.openapi.version}")
	private String version;

	@Value("${spaceship.openapi.description}")
	private String description;

	@Value("${spaceship.openapi.terms-of-service}")
	private String termsOfService;

	@Bean
	public GroupedOpenApi externalOpenApi() {
		return GroupedOpenApi.builder()
				.group("external")
				.packagesToScan(external)
				.packagesToExclude(internal)
				.addOpenApiCustomiser(new GroupExternalCustomiser(title, version, description, termsOfService))
				.build();
	}

	@Bean
	public GroupedOpenApi internalOpenApi() {
		return GroupedOpenApi.builder()
				.group("internal")
				.packagesToScan(internal)
				.addOpenApiCustomiser(new GroupInternalCustomiser(title, version, description, termsOfService))
				.build();
	}
}
