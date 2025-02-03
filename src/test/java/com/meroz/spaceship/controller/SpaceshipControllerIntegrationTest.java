package com.meroz.spaceship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meroz.spaceship.config.SecurityConfig;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.service.SpaceshipService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(SecurityConfig.class)
@WebMvcTest(SpaceshipController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SpaceshipControllerIntegrationTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private SpaceshipService spaceshipService;

	@Test
	@WithMockUser
	public void getAllIntegrationTest() throws Exception {
		Page<SpaceshipResponse> responseMock = new PageImpl<>(getSpaceshipListContainingName());

		when(spaceshipService.getAllSpaceships(any())).thenReturn(responseMock);

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/spaceships")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void getByIdIntegrationTest() {
		/*ResponseEntity<SpaceshipResponse> response = mockMvc.perform(MockMvcRequestBuilders.get( "/spaceships/1L").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());

		 */
	}


	private static List<SpaceshipResponse> getSpaceshipListContainingName() {
		return List.of(getSpaceship(), getSpaceship2());
	}

	private static SpaceshipResponse getSpaceship2() {
		SpaceshipResponse toReturn = new SpaceshipResponse();

		toReturn.setId(2L);
		toReturn.setName("Umberto");
		toReturn.setSeries("Star wars");
		toReturn.setManufacturer("Kuat");

		return toReturn;
	}

	private static SpaceshipResponse getSpaceship() {
		SpaceshipResponse toReturn = new SpaceshipResponse();

		toReturn.setId(1L);
		toReturn.setName("test");
		toReturn.setSeries("test");
		toReturn.setManufacturer("test");

		return toReturn;
	}
}
