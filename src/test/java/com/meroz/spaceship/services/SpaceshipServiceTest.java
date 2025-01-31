package com.meroz.spaceship.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.entities.Spaceship;
import com.meroz.spaceship.mapper.SpaceshipMapper;
import com.meroz.spaceship.repository.SpaceshipRepository;
import com.meroz.spaceship.service.SpaceshipService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class SpaceshipServiceTest {

	private final SpaceshipMapper spaceshipMapper = SpaceshipMapper.SPACESHIP_MAPPER;

	@InjectMocks
	private SpaceshipService spaceshipService;

	@Mock
	private SpaceshipRepository spaceshipRepository;

	private static Spaceship spaceship;

	private static Spaceship spaceshipUpdate;

	private static List<Spaceship> spaceshipListContainingName;

	private static Page<Spaceship> spaceshipPage;

	@BeforeAll
	public static void beforeAll() {
		spaceship = getSpaceship();
		spaceshipUpdate = getSpaceshipUpdate();
		spaceshipListContainingName = getSpaceshipListContainingName();
		spaceshipPage = getSpaceshipPage(spaceshipListContainingName);
	}

	@Test
	void findAll() {
		when(spaceshipRepository.findAll((Pageable) any())).thenReturn(spaceshipPage);

		Page<SpaceshipResponse> toValidate = spaceshipService.getAllSpaceships(PageRequest.of(0, 10));

		Assertions.assertEquals(2, toValidate.getContent().size());
		Assertions.assertEquals(SpaceshipResponse.class, toValidate.getContent().getFirst().getClass());
	}

	@Test
	void findById() {
		when(spaceshipRepository.findById(any())).thenReturn(Optional.of(spaceship));

		SpaceshipResponse toValidate = spaceshipService.getSpaceshipById(1L);

		Assertions.assertEquals(1L, toValidate.getId());
		Assertions.assertEquals(SpaceshipResponse.class, toValidate.getClass());
		Assertions.assertEquals("test", toValidate.getName());
	}

	@Test
	void findByIdClassValidation() {
		when(spaceshipRepository.findById(any())).thenReturn(Optional.of(spaceship));

		SpaceshipResponse response = spaceshipService.getSpaceshipById(1L);
		SpaceshipResponse toValidate = spaceshipMapper.toResponse(spaceship);

		Assertions.assertEquals(response.getId(), toValidate.getId());
		Assertions.assertEquals(response.getName(), toValidate.getName());
		Assertions.assertEquals(response.getSeries(), toValidate.getSeries());
		Assertions.assertEquals(response.getManufacturer(), toValidate.getManufacturer());
	}

	@Test
	void findByContainInName() {
		when(spaceshipRepository.findSpaceshipsByNameContains(any())).thenReturn(spaceshipListContainingName);

		List<SpaceshipResponse> toValidate = spaceshipService.getSpaceshipByContainName("t");
		Assertions.assertEquals(2, toValidate.size());
	}

	@Test
	void creatingSpaceship() {
		when(spaceshipRepository.save(any())).thenReturn(spaceship);

		SpaceshipResponse toValidate = spaceshipService.createSpaceship(SpaceshipRequest.builder().name("test").manufacturer("test").series("test").build());

		Assertions.assertEquals(SpaceshipResponse.class, toValidate.getClass());
		Mockito.verify(spaceshipRepository, Mockito.atLeastOnce()).save(any());
	}

	@Test
	void updatingSpaceship() {
		when(spaceshipRepository.findById(any())).thenReturn(Optional.of(spaceship));
		when(spaceshipRepository.save(any())).thenReturn(spaceshipUpdate);

		SpaceshipRequest request = SpaceshipRequest.builder().name("test2").manufacturer("test2").series("test2").build();

		SpaceshipResponse toValidate = spaceshipService.updateSpaceship(1L, request);

		Assertions.assertEquals(SpaceshipResponse.class, toValidate.getClass());
		Mockito.verify(spaceshipRepository, Mockito.atLeastOnce()).findById(1L);
		Mockito.verify(spaceshipRepository, Mockito.atLeastOnce()).save(any());
	}

	@Test
	void deletingSpaceship() {
		final Long spaceshipId = 1L;
		
		spaceshipService.deleteSpaceship(spaceshipId);
		
		Mockito.verify(spaceshipRepository, Mockito.atLeastOnce()).deleteById(any());
	}

	private static PageImpl<Spaceship> getSpaceshipPage(List<Spaceship> spaceshipList) {
		return new PageImpl<>(spaceshipList);
	}

	private static List<Spaceship> getSpaceshipListContainingName() {
		return List.of(getSpaceship(), getSpaceship2());
	}

	private static List<Spaceship> getSpaceshipList() {
		return List.of(getSpaceship());
	}

	private static Spaceship getSpaceshipUpdate() {
		Spaceship toReturn = new Spaceship();

		toReturn.setId(1L);
		toReturn.setName("test");
		toReturn.setSeries("test");
		toReturn.setManufacturer("test");

		return toReturn;
	}

	private static Spaceship getSpaceship2() {
		Spaceship toReturn = new Spaceship();

		toReturn.setId(2L);
		toReturn.setName("Umberto");
		toReturn.setSeries("Star wars");
		toReturn.setManufacturer("Kuat");

		return toReturn;
	}

	private static Spaceship getSpaceship() {
		Spaceship toReturn = new Spaceship();

		toReturn.setId(1L);
		toReturn.setName("test");
		toReturn.setSeries("test");
		toReturn.setManufacturer("test");

		return toReturn;
	}
}
