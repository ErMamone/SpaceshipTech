package com.meroz.spaceship.config.security.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserToCreate implements Serializable {

	@Size(min = 4)
	private String name;

	private String username;

	@Size(min = 8)
	private String password;

	@Size(min = 8)
	private String repeatedPassword;

	@Builder.Default
	private Boolean isAdmin = Boolean.FALSE;
}
