package com.meroz.spaceship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.entities.Spaceship;
import com.meroz.spaceship.repository.SpaceshipRepository;
import com.meroz.spaceship.service.SpaceshipService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@Transactional
@Testcontainers
public class SpaceshipControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SpaceshipService spaceshipService;

	@Autowired
	private SpaceshipRepository spaceshipRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static final String QUEUE_NAME = "create-spaceship-queue";

	@Container
	static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.9-management");

	@DynamicPropertySource
	static void configureRabbitMQ(DynamicPropertyRegistry registry) {
		registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
		registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
	}

	public void setUp() {
		spaceshipRepository.save(spaceshipToDb());
		spaceshipRepository.save(spaceshipToDb2());
	}

	@Test
	public void shouldRejectDeletingReviewsWhenUserIsNotAdmin() throws Exception {
		this.mockMvc.perform(delete("/spaceships/1").with(csrf())).andExpect(status().isForbidden());
	}

	//@Test
	@WithMockUser
	public void deleteNonExistingIntegrationTest() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.delete("/spaceships/{id}", -1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser
	public void notFoundException() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/spaceships/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser
	public void getAllIntegrationTest() throws Exception {
		this.setUp();

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/spaceships")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	//@Test
	@WithMockUser
	public void getByIdIntegrationTest() throws Exception {
		this.setUp();

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/spaceships/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("test"));
	}

	@Test
	@WithMockUser
	public void getByNameIntegrationTest() throws Exception {
		this.setUp();

		ResultActions result = this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/spaceships/name")
						.queryParam("textToSearch", "test")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertTrue(result.andReturn().getResponse().getContentAsString().contains("test"));
	}

	@Test
	@WithMockUser
	public void creationIntegrationTest() throws Exception {
		String jsonRequest = objectMapper.writeValueAsString(getSpaceshipRequest());

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/spaceships")
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void updateIntegrationTest() throws Exception {
		this.setUp();
		String jsonRequest = objectMapper.writeValueAsString(getSpaceshipRequest());

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.put("/spaceships/{id}", 1L)
						.content(jsonRequest)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void deleteIntegrationTest() throws Exception {
		this.setUp();

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.delete("/spaceships/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		boolean exists = this.spaceshipRepository.existsById(1L);
		assertFalse(exists, "Spaceship with id " + 1L + " does not exist");
	}

	@Test
	@WithMockUser
	public void rabbitMigrationIntegrationTest() throws Exception {
		this.setUp();

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/spaceships/migrationByRabbit")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Object message = rabbitTemplate.receiveAndConvert(QUEUE_NAME, 10000);
		assertNotNull(message, "Message is null");
	}

	private static SpaceshipRequest getSpaceshipRequest() {
		SpaceshipRequest toReturn = new SpaceshipRequest();

		toReturn.setName("AB-00");
		toReturn.setSeries("Interestellar");
		toReturn.setManufacturer("NASA");

		return toReturn;
	}

	private static Spaceship spaceshipToDb2() {
		Spaceship toReturn = new Spaceship();

		toReturn.setName("Umberto");
		toReturn.setSeries("Star wars");
		toReturn.setManufacturer("Kuat");

		return toReturn;
	}

	private static Spaceship spaceshipToDb() {
		Spaceship toReturn = new Spaceship();

		toReturn.setName("test");
		toReturn.setSeries("test");
		toReturn.setManufacturer("test");

		return toReturn;
	}
}
