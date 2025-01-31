package com.meroz.spaceship.config;

import com.meroz.spaceship.controller.response.ExceptionResponses;
import com.meroz.spaceship.exception.NotFoundBusinessException;
import com.meroz.spaceship.exception.SpaceshipBusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(NotFoundBusinessException.class)
	public ResponseEntity<ExceptionResponses> handleNotFoundException(SpaceshipBusinessException ex) {
		ExceptionResponses response = new ExceptionResponses(ex.getError().status().value(), ex.getError().code().toString(), ex.getError().message());
		return ResponseEntity.status(ex.getError().status()).body(response);
	}

	@ExceptionHandler(exception = RuntimeException.class, value = Exception.class)
	public ResponseEntity<ExceptionResponses> handleRuntimeException(SpaceshipBusinessException ex) {
		ExceptionResponses response = new ExceptionResponses(ex.getError().status().value(), ex.getError().code().toString(), ex.getError().message());
		return ResponseEntity.status(ex.getError().status()).body(response);
	}
}
