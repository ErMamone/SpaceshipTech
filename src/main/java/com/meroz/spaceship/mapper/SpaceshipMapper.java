package com.meroz.spaceship.mapper;

import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.entities.Spaceship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpaceshipMapper {
	SpaceshipMapper SPACESHIP_MAPPER = Mappers.getMapper(SpaceshipMapper.class);

	Spaceship fromRequest(SpaceshipRequest request);

	SpaceshipResponse toResponse(Spaceship spaceship);

	@Mapping(target = "id", ignore = true)
	Spaceship merge(Spaceship spaceship, SpaceshipRequest request);
}
