package com.meroz.spaceship.utils.enums;

import com.meroz.spaceship.config.exception.SpaceshipEnumError;
import org.springframework.http.HttpStatus;

public enum ErrorsEnum implements SpaceshipEnumError {
	FORBIDDEN(1403, "Forbidden", HttpStatus.FORBIDDEN),
	NOT_FOUND(1404, "Not Found", HttpStatus.NOT_FOUND),
	SERVICE_UNAVAILABLE(1503, "Service Unavailable", HttpStatus.SERVICE_UNAVAILABLE),
	INTERNAL_SERVER_ERROR(1500, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
	BAD_REQUEST(1400, "Bad Request", HttpStatus.BAD_REQUEST),
	RABBIT_MSG_SEND_FAILED(1510, "Rabbit Msg Send Failed", HttpStatus.INTERNAL_SERVER_ERROR),
	SECURITY_EXCEPTION(1401, "Security Exception", HttpStatus.UNAUTHORIZED),
	PASSWORD_NOT_CORRECT(1402, "Password Not Correct", HttpStatus.UNAUTHORIZED),
	;

	ErrorsEnum(Integer code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.status = httpStatus;
	}

	private final Integer code;
	private final String message;
	private final HttpStatus status;

	@Override
	public Integer code() {
		return this.code;
	}

	@Override
	public String message() {
		return this.message;
	}

	@Override
	public HttpStatus status() {
		return this.status;
	}
}
