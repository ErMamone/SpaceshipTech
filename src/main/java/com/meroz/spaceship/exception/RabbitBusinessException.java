package com.meroz.spaceship.exception;

import com.meroz.spaceship.config.exception.SpaceshipEnumError;
import lombok.Getter;

@Getter
public class RabbitBusinessException extends SpaceshipBusinessException {

	protected final transient SpaceshipEnumError error;
	protected transient Object[] params;

	public RabbitBusinessException(SpaceshipEnumError error) {
		super(error);
		this.error = error;
	}

	public RabbitBusinessException(SpaceshipEnumError error, Object[] params) {
		super(error, params);
		this.error = error;
		this.params = params;
	}

}
