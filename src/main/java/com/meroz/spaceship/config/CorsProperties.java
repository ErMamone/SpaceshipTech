package com.meroz.spaceship.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Getter
@Setter
@Configuration
@RequiredArgsConstructor
public class CorsProperties {

	private final Cors cors;

	public CorsConfiguration getCorsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(cors.getAllowedOrigins());
		corsConfiguration.setAllowedHeaders(cors.getAllowedHeaders());
		corsConfiguration.setAllowedMethods(cors.getAllowedMethods());
		corsConfiguration.setAllowCredentials(cors.getAllowCredentials());
		corsConfiguration.setMaxAge(cors.getMaxAge());
		return corsConfiguration;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", getCorsConfiguration());
		return source;
	}
}
