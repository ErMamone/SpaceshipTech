package com.meroz.spaceship.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meroz.spaceship.entities.Spaceship;
import com.meroz.spaceship.exception.RabbitBusinessException;
import com.meroz.spaceship.utils.enums.ErrorsEnum;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.meroz.spaceship.config.RabbitConfig.CREATE_SPACESHIP_EXCHANGE;
import static com.meroz.spaceship.config.RabbitConfig.CREATE_SPACESHIP_ROUTE_KEY;
import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

@Log4j2
@Service
@AllArgsConstructor
public class RabbitQueueProducerManager {

   private final ObjectMapper objectMapper;

   private final RabbitTemplate rabbitTemplate;

   public void createAllSpaceship(List<Spaceship> spaceships){
      try {
         log.info("creation spaceship");

         MessageProperties props = MessagePropertiesBuilder.newInstance().setContentType(CONTENT_TYPE_JSON).build();
         String jsonData = objectMapper.writeValueAsString(spaceships);
         Message msg = new Message(jsonData.getBytes(), props);
         rabbitTemplate.send(CREATE_SPACESHIP_EXCHANGE, CREATE_SPACESHIP_ROUTE_KEY, msg);

      } catch (Exception e) {
         throw new RabbitBusinessException(ErrorsEnum.RABBIT_MSG_SEND_FAILED, new Object[]{e.getMessage()});
      }
   }
}