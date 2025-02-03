package com.meroz.spaceship.config;

import com.meroz.spaceship.controller.response.ExceptionResponses;
import com.meroz.spaceship.exception.GeneralSecurityException;
import com.meroz.spaceship.exception.LoginSecurityException;
import com.meroz.spaceship.exception.NotFoundBusinessException;
import com.meroz.spaceship.exception.RabbitBusinessException;
import com.meroz.spaceship.exception.SpaceshipBusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SpaceshipControllerAdvice {

	//TODO integrar messageSource, repasar

	@ExceptionHandler({NotFoundBusinessException.class})
	public ResponseEntity<ExceptionResponses> handleNotFoundException(SpaceshipBusinessException ex) {
		ExceptionResponses response = new ExceptionResponses(ex.getError().status().value(), ex.getError().code().toString(), ex.getError().message());
		return ResponseEntity.status(ex.getError().status()).body(response);
	}

	@ExceptionHandler({RabbitBusinessException.class})
	public ResponseEntity<ExceptionResponses> handleRabbitException(SpaceshipBusinessException ex) {
		ExceptionResponses response = new ExceptionResponses(ex.getError().status().value(), ex.getError().code().toString(), ex.getError().message());
		return ResponseEntity.status(ex.getError().status()).body(response);
	}

	@ExceptionHandler({GeneralSecurityException.class, LoginSecurityException.class})
	public ResponseEntity<ExceptionResponses> handleSecurityException(SpaceshipBusinessException ex) {
		ExceptionResponses response = new ExceptionResponses(ex.getError().status().value(), ex.getError().code().toString(), ex.getError().message());
		return ResponseEntity.status(ex.getError().status()).body(response);
	}

	@ExceptionHandler({RuntimeException.class, Exception.class})
	public ResponseEntity<ExceptionResponses> handleRuntimeException(SpaceshipBusinessException ex) {
		ExceptionResponses response = new ExceptionResponses(ex.getError().status().value(), ex.getError().code().toString(), ex.getError().message());
		return ResponseEntity.status(ex.getError().status()).body(response);
	}
}
