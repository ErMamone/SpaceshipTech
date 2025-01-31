package com.meroz.spaceship.service;

import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.entities.Spaceship;
import com.meroz.spaceship.mapper.SpaceshipMapper;
import com.meroz.spaceship.repository.SpaceshipRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SpaceshipService {

	private final SpaceshipRepository spaceshipRepository;

	private static final SpaceshipMapper spaceshipMapper = SpaceshipMapper.SPACESHIP_MAPPER;

	public Page<SpaceshipResponse> getAllSpaceships(Pageable page) {
		return spaceshipRepository.findAll(page).map(spaceshipMapper::toResponse);
	}

	public SpaceshipResponse getSpaceshipById(Long id) {
		Spaceship spaceship = spaceshipRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Spaceship not found"));
		return spaceshipMapper.toResponse(spaceship);
	}

	public SpaceshipResponse getSpaceshipByContainName(String name) {
		Spaceship spaceship = spaceshipRepository.findByNameContains(name)
				.orElseThrow(() -> new NoSuchElementException("Spaceship not found"));
		return spaceshipMapper.toResponse(spaceship);
	}

	public SpaceshipResponse createSpaceship(SpaceshipRequest request) {
		Spaceship spaceship = spaceshipMapper.fromRequest(request);
		return spaceshipMapper.toResponse(spaceshipRepository.save(spaceship));
	}

	public SpaceshipResponse updateSpaceship(Long id, SpaceshipRequest request) {
		Spaceship spaceship = spaceshipRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Spaceship not found"));

		spaceship = spaceshipMapper.merge(spaceship, request);

		return spaceshipMapper.toResponse(spaceshipRepository.save(spaceship));
	}

	public void deleteSpaceship(Long id) {
		spaceshipRepository.deleteById(id);
	}


}
