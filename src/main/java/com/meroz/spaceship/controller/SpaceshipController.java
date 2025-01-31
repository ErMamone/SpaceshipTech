package com.meroz.spaceship.controller;

import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.service.SpaceshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/spaceships")
public class SpaceshipController {

	private final SpaceshipService spaceshipService;

	//TODO: Agregar Documentacion con Swagger.
	@GetMapping
	public ResponseEntity<Page<SpaceshipResponse>> getAllSpaceships(Pageable page) {
		return ResponseEntity.ok(spaceshipService.getAllSpaceships(page));
	}

	@GetMapping("/{id}")
	public ResponseEntity<SpaceshipResponse> getSpaceshipById(@PathVariable Long id) {
		return ResponseEntity.ok(spaceshipService.getSpaceshipById(id));
	}

	@GetMapping("/name")
	public ResponseEntity<List<SpaceshipResponse>> getSpaceshipById(@RequestParam(value = "textToSearch") String textToSearch) {
		return ResponseEntity.ok(spaceshipService.getSpaceshipByContainName(textToSearch));
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