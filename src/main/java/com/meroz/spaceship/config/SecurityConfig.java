package com.meroz.spaceship.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

	private String publicKeyUri;

	private String resourceId;

	private String codePrefix;

	private static final String CLAIM_KEY_AUTHORITIES = "authorities";
	private static final String AUTHORITIES_PREFIX = "";

	//TODO:Implementar Security y spring security
}
