package com.meroz.spaceship.service;

import com.meroz.spaceship.config.security.request.UserToCreate;
import com.meroz.spaceship.entities.User;
import com.meroz.spaceship.exception.LoginSecurityException;
import com.meroz.spaceship.mapper.UserMapper;
import com.meroz.spaceship.repository.UserRepository;
import com.meroz.spaceship.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.meroz.spaceship.utils.enums.ErrorsEnum.PASSWORD_NOT_CORRECT;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private static final UserMapper userMapper = UserMapper.USER_MAPPER;

	public User userRegister(UserToCreate newUser) {
		validatePassword(newUser);

		User user = userMapper.fromRequest(newUser);
		user.setPassword(passwordEncoder.encode(newUser.getPassword()));
		user.setRole(newUser.getIsAdmin() ? Role.ROLE_ADMIN : Role.ROLE_USER);

		return userRepository.save(user);
	}

	public Optional<User> findOneByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	private void validatePassword(UserToCreate dto) {

		if (!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatedPassword())) {
			throw new LoginSecurityException(PASSWORD_NOT_CORRECT);
		}

		if (!dto.getPassword().equals(dto.getRepeatedPassword())) {
			throw new LoginSecurityException(PASSWORD_NOT_CORRECT);
		}

	}

}
