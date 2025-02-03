package com.meroz.spaceship.config.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class SpaceshipAudienceValidator implements OAuth2TokenValidator<Jwt> {

	private String resourceId;

	OAuth2Error error = new OAuth2Error("missing_aud", "No posees acceso al recurso", null);

	public SpaceshipAudienceValidator(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public OAuth2TokenValidatorResult validate(Jwt jwt) {
		return jwt.getAudience().contains(this.resourceId) ? OAuth2TokenValidatorResult.success() : OAuth2TokenValidatorResult.failure(this.error);
	}
}
