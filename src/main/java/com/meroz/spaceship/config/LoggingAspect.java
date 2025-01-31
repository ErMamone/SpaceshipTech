package com.meroz.spaceship.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

	@Pointcut("execution(* com.meroz.spaceship.service.SpaceshipService.getSpaceshipById(..)) && args(id)")
	public void getById(Long id) {}

	@Before(value = "getById(id)", argNames = "id")
	public void logNegativeId(Long id) {
		if (id < 0) {
			log.warn("Attempted to fetch spaceship with negative ID: {}", id);
		}
	}
}
