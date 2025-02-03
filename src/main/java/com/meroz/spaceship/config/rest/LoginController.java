package com.meroz.spaceship.config.rest;

import com.meroz.spaceship.config.security.request.AuthenticationRequest;
import com.meroz.spaceship.config.security.request.UserToCreate;
import com.meroz.spaceship.config.security.response.AuthenticationResponse;
import com.meroz.spaceship.config.security.response.RegisteredUser;
import com.meroz.spaceship.config.security.service.AuthenticationService;
import com.meroz.spaceship.entities.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class LoginController {

	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<RegisteredUser> register(@RequestBody @Valid UserToCreate newUser) {
		RegisteredUser register = authenticationService.registerOneCustomer(newUser);
		return ResponseEntity.ok(register);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
		AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
		return ResponseEntity.ok(rsp);
	}

	@GetMapping("/validate-token")
	public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
		boolean isTokenValid = authenticationService.validateToken(jwt);
		return ResponseEntity.ok(isTokenValid);
	}

	@GetMapping("/profile")
	public ResponseEntity<User> findMyProfile() {
		User user = authenticationService.findLoggedInUser();
		return ResponseEntity.ok(user);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> findAllUsers() {
		List<User> user = authenticationService.findAll();
		return ResponseEntity.ok(user);
	}
}