package com.meroz.spaceship.exception;

import com.meroz.spaceship.config.exception.SpaceshipEnumError;
import lombok.Getter;

@Getter
public abstract class SpaceshipBusinessException extends RuntimeException {

	private final transient SpaceshipEnumError error;
	private transient Object[] params;

	public SpaceshipBusinessException(String message) {
		super(message);
		this.error = null;
	}

	public SpaceshipBusinessException(SpaceshipEnumError error, Object... params) {
		super(error.message());
		this.error = error;
		this.params = params;
	}

	public SpaceshipBusinessException(SpaceshipEnumError error) {
		this.error = error;
	}
}
