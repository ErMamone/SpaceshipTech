package com.meroz.spaceship.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.meroz.spaceship.config.security.SpaceshipAudienceValidator;
import com.meroz.spaceship.config.security.SpaceshipPublicKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Value("${spring.security.oauth2.resourceserver.jwt.key-uri}")
	private String publicKeyUri;

	@Value("${spaceship.security.aud}")
	private String resourceId;

	private static final String CLAIM_KEY_AUTHORITIES = "authorities";
	private static final String AUTHORITIES_PREFIX = "";

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests.requestMatchers("/swagger-ui/**",
						"/metrics",
						"/metrics/**",
						"/**",
						"/v3/api-docs/**").permitAll()
				.anyRequest().authenticated())
				.csrf(AbstractHttpConfigurer::disable)
				.cors(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(getJwtAuthConverter())));

		return http.build();
	}

	@Bean
	JwtDecoder getJwtDecoder() throws JsonProcessingException {
		OAuth2TokenValidator<Jwt> audienceValidator = new SpaceshipAudienceValidator(resourceId);
		OAuth2TokenValidator<Jwt> withStandards = JwtValidators.createDefault();
		OAuth2TokenValidator<Jwt> withBoth = new DelegatingOAuth2TokenValidator<>(withStandards, audienceValidator);

		NimbusJwtDecoder decoder = NimbusJwtDecoder.withPublicKey(SpaceshipPublicKeyGenerator.createPublicRSAKeyFromUri(this.publicKeyUri)).build();
		decoder.setJwtValidator(withBoth);

		return decoder;
	}

	private JwtAuthenticationConverter getJwtAuthConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(CLAIM_KEY_AUTHORITIES);
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AUTHORITIES_PREFIX);

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

		return jwtAuthenticationConverter;
	}

	@Bean
	AuditorAware<String> securityContextHolderAuditorAware() {
		return () -> Optional.ofNullable(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("user"));
	}


}
