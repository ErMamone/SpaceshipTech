package com.meroz.spaceship.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "spaceship.cors")
@Component
public class Cors {

	private List<String> allowedOrigins;
	private List<String> allowedMethods;
	private List<String> allowedHeaders;
	private List<String> exposedHeaders;
	private Boolean allowCredentials;
	private Long maxAge;
}
