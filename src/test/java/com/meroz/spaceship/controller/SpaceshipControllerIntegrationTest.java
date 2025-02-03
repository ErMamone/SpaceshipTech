package com.meroz.spaceship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meroz.spaceship.config.SecurityConfig;
import com.meroz.spaceship.config.security.service.JwtService;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.service.SpaceshipService;
import com.meroz.spaceship.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, JwtService.class})
@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
public class SpaceshipControllerIntegrationTest {


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private SpaceshipService spaceshipService;

	@Mock
	private UserService userService;

	@Test
	public void shouldRejectDeletingReviewsWhenUserIsNotAdmin() throws Exception {
		this.mockMvc.perform(delete("/spaceships/1").with(csrf())).andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser
	public void getAllIntegrationTest() throws Exception {
		Page<SpaceshipResponse> responseMock = new PageImpl<>(getSpaceshipListContainingName());

		when(spaceshipService.getAllSpaceships(any())).thenReturn(responseMock);

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/spaceships")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

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
