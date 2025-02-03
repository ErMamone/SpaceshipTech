package com.meroz.spaceship.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String CREATE_SPACESHIP_QUEUE = "create-spaceship-queue";

	public static final String CREATE_SPACESHIP_EXCHANGE = "create-spaceship-exchange";

	public static final String CREATE_SPACESHIP_ROUTE_KEY = "create-spaceship-key";

	public static final String UPDATE_SPACESHIP_QUEUE = "update-spaceship-queue";

	public static final String UPDATE_SPACESHIP_EXCHANGE = "update-spaceship-exchange";

	public static final String UPDATE_SPACESHIP_ROUTE_KEY = "update-spaceship-key";

	@Bean
	Queue createSpaceshipQueue() {
		return new Queue(CREATE_SPACESHIP_QUEUE, true);
	}

	@Bean
	DirectExchange createSpaceshipExchange() {
		return new DirectExchange(CREATE_SPACESHIP_EXCHANGE);
	}

	@Bean
	Binding createSpaceshipBinding(Queue createSpaceshipQueue, DirectExchange createSpaceshipExchange) {
		return BindingBuilder.bind(createSpaceshipQueue).to(createSpaceshipExchange).with(CREATE_SPACESHIP_ROUTE_KEY);
	}

	@Bean
	Queue updateSpaceshipQueue() {
		return new Queue(UPDATE_SPACESHIP_QUEUE, true);
	}

	@Bean
	DirectExchange updateSpaceshipExchange() {
		return new DirectExchange(UPDATE_SPACESHIP_EXCHANGE);
	}

	@Bean
	Binding updateSpaceshipBinding(Queue updateSpaceshipQueue, DirectExchange updateSpaceshipExchange) {
		return BindingBuilder.bind(updateSpaceshipQueue).to(updateSpaceshipExchange).with(UPDATE_SPACESHIP_ROUTE_KEY);
	}
}
