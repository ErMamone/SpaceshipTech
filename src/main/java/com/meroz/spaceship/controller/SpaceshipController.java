package com.meroz.spaceship.controller;

import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.service.SpaceshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@AllArgsConstructor
@RequestMapping("/spaceships")
public class SpaceshipController {

	private final SpaceshipService spaceshipService;

	@GetMapping
	public ResponseEntity<Page<SpaceshipResponse>> getAllSpaceships(Pageable pageable) {
		return ResponseEntity.ok(spaceshipService.getAllSpaceships(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<SpaceshipResponse> getSpaceshipById(@PathVariable Long id) {
		return ResponseEntity.ok(spaceshipService.getSpaceshipById(id));
	}

	@GetMapping("/{search}")
	public ResponseEntity<SpaceshipResponse> getSpaceshipById(@PathVariable String search) {
		return ResponseEntity.ok(spaceshipService.getSpaceshipByContainName(search));
	}

	@PostMapping
	public ResponseEntity<SpaceshipResponse> createSpaceship(@RequestBody SpaceshipRequest spaceshipDto) {
		return ResponseEntity.ok(spaceshipService.createSpaceship(spaceshipDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SpaceshipResponse> updateSpaceship(@PathVariable Long id, @RequestBody SpaceshipRequest spaceshipDto) {
		return ResponseEntity.ok(spaceshipService.updateSpaceship(id, spaceshipDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSpaceship(@PathVariable Long id) {
		spaceshipService.deleteSpaceship(id);
		return ResponseEntity.noContent().build();
	}
}