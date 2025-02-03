package com.meroz.spaceship.config.security.service;

import com.meroz.spaceship.config.security.request.AuthenticationRequest;
import com.meroz.spaceship.config.security.request.UserToCreate;
import com.meroz.spaceship.config.security.response.AuthenticationResponse;
import com.meroz.spaceship.config.security.response.RegisteredUser;
import com.meroz.spaceship.entities.User;
import com.meroz.spaceship.exception.NotFoundBusinessException;
import com.meroz.spaceship.mapper.UserMapper;
import com.meroz.spaceship.repository.UserRepository;
import com.meroz.spaceship.service.UserService;
import com.meroz.spaceship.utils.enums.ErrorsEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;

	private final UserService userService;

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	private static final UserMapper userMapper = UserMapper.USER_MAPPER;

	public RegisteredUser registerOneCustomer(UserToCreate newUser) {
		User user = userService.userRegister(newUser);

		RegisteredUser userDto = userMapper.toRegistered(user);

		String jwt = jwtService.generateToken(user, generateExtraClaims(user));
		userDto.setJwt(jwt);

		return userDto;
	}

	private Map<String, Object> generateExtraClaims(User user) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("name", user.getName());
		extraClaims.put("role", user.getRole().name());
		extraClaims.put("authorities", user.getAuthorities());

		return extraClaims;
	}

	public AuthenticationResponse login(AuthenticationRequest autRequest) {

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				autRequest.getUsername(), autRequest.getPassword()
		);

		authenticationManager.authenticate(authentication);

		User user = userService.findOneByUsername(autRequest.getUsername()).orElseThrow();
		String jwt = jwtService.generateToken(user, generateExtraClaims(user));

		AuthenticationResponse authRsp = new AuthenticationResponse();
		authRsp.setJwt(jwt);

		return authRsp;
	}

	public boolean validateToken(String jwt) {

		try {
			jwtService.extractUsername(jwt);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public User findLoggedInUser() {

		UsernamePasswordAuthenticationToken auth =
				(UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		String username = (String) auth.getPrincipal();
		
		return userService.findOneByUsername(username)
				.orElseThrow(() -> new NotFoundBusinessException(ErrorsEnum.NOT_FOUND, new Object[]{username}));
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}
}
