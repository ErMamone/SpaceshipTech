package com.meroz.spaceship.service;

import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.entities.Spaceship;
import com.meroz.spaceship.exception.NotFoundBusinessException;
import com.meroz.spaceship.mapper.SpaceshipMapper;
import com.meroz.spaceship.repository.SpaceshipRepository;
import com.meroz.spaceship.utils.enums.ErrorsEnum;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.meroz.spaceship.config.CacheConfig.SPACESHIP_NAMES;

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
				.orElseThrow(() -> new NotFoundBusinessException(ErrorsEnum.NOT_FOUND, new Object[]{id}));
		return spaceshipMapper.toResponse(spaceship);
	}

	@Cacheable(cacheNames = SPACESHIP_NAMES)
	public List<SpaceshipResponse> getSpaceshipByContainName(String name) {
		return spaceshipRepository.findSpaceshipsByNameContains(name).stream().map(spaceshipMapper::toResponse).toList();
	}

	//TODO: implementar rabbit para la creacion
	public SpaceshipResponse createSpaceship(SpaceshipRequest request) {
		Spaceship spaceship = spaceshipMapper.fromRequest(request);
		return spaceshipMapper.toResponse(spaceshipRepository.save(spaceship));
	}

	public SpaceshipResponse updateSpaceship(Long id, SpaceshipRequest request) {
		Spaceship spaceship = spaceshipRepository.findById(id)
				.orElseThrow(() -> new NotFoundBusinessException(ErrorsEnum.NOT_FOUND, new Object[]{id, request}));

		spaceship = spaceshipMapper.merge(spaceship, request);

		return spaceshipMapper.toResponse(spaceshipRepository.save(spaceship));
	}

	public void deleteSpaceship(Long id) {
		spaceshipRepository.deleteById(id);
	}


}
