package com.meroz.spaceship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meroz.spaceship.config.CustomTestConfig;
import com.meroz.spaceship.service.SpaceshipService;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SpaceshipController.class)
@ContextConfiguration(classes = {CustomTestConfig.ControllerTestConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class SpaceshipControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private SpaceshipService spaceshipService;


	@Test
	@WithMockUser
	public void givenWac_whenServletContext_thenItProvidesGreetController() {
		ServletContext servletContext = mockMvc.getDispatcherServlet().getServletContext();

		assertNotNull(servletContext);
		assertInstanceOf(MockServletContext.class, servletContext);
	}

	@Test
	@WithMockUser
	public void controllerTest() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/spaceship"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Hello World!!!"))
				.andReturn();

		assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
	}
}
