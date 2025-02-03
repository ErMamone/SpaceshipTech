package com.meroz.spaceship.mapper;

import com.meroz.spaceship.config.security.request.UserToCreate;
import com.meroz.spaceship.config.security.response.RegisteredUser;
import com.meroz.spaceship.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
	UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "role", ignore = true)
	User fromRequest(UserToCreate request);

	RegisteredUser toRegistered(User created);

}
