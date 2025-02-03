package com.meroz.spaceship.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration(exclude = {SecurityConfig.class})
@Configuration
public class ControllerTestConfig {
}
