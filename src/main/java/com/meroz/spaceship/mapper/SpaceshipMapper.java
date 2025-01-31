package com.meroz.spaceship.mapper;

import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.entities.Spaceship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SpaceshipMapper {
	SpaceshipMapper SPACESHIP_MAPPER = Mappers.getMapper(SpaceshipMapper.class);

	Spaceship fromRequest(SpaceshipRequest request);

	SpaceshipResponse toResponse(Spaceship spaceship);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "name", source = "request.name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "series", source = "request.series", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "manufacturer", source = "request.manufacturer", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Spaceship merge(@MappingTarget Spaceship spaceship, SpaceshipRequest request);
}
