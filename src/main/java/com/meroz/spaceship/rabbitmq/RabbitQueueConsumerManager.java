package com.meroz.spaceship.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meroz.spaceship.exception.RabbitBusinessException;
import com.meroz.spaceship.rabbitmq.messages.SpaceshipMessage;
import com.meroz.spaceship.service.SpaceshipService;
import com.meroz.spaceship.utils.enums.ErrorsEnum;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.meroz.spaceship.config.RabbitConfig.UPDATE_SPACESHIP_QUEUE;

@Component
@Log4j2
@AllArgsConstructor
public class RabbitQueueConsumerManager {

	private final ObjectMapper objectMapper;

	private final SpaceshipService spaceshipService;

	@RabbitListener(queues = UPDATE_SPACESHIP_QUEUE)
	public void billPaymentMessageConsumer(byte[] data) {
		try {
			SpaceshipMessage requestData = objectMapper.readValue(new String(data), SpaceshipMessage.class);
			log.info("Received Spaceship Message {} ", requestData);
			spaceshipService.updateByRabbit(requestData);

		} catch (Exception e) {
			log.error("Spaceship consumer failed", e);
			throw new RabbitBusinessException(ErrorsEnum.INTERNAL_SERVER_ERROR, new Object[]{e.getMessage()});
		}
	}

}
