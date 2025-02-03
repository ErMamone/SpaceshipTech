package com.meroz.spaceship.config;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;

@Configuration
@AllArgsConstructor
public class CacheConfig {

	public static final String SPACESHIP_NAMES = "spaceshipNames";

	private final CacheManager cacheManager;

	@Scheduled(fixedRate = 120000)
	public void cacheCleanUp() {
		Objects.requireNonNull(cacheManager.getCache(SPACESHIP_NAMES)).clear();
	}
}
