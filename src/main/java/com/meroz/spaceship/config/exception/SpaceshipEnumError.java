package com.meroz.spaceship.config.exception;

import org.springframework.http.HttpStatus;

public interface SpaceshipEnumError {
	Integer code();

	String message();

	HttpStatus status();
}
