package com.meroz.spaceship.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
public class CustomTestConfig {

	private CustomTestConfig() {
	}

	@Configuration
	@Import(value = {SpaceshipControllerAdvice.class})
	@ComponentScan(basePackages = {"com.meroz.spaceship.controller"}, lazyInit = true)
	public static class ControllerTestConfiguration {
	}
}
