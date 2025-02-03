package com.meroz.spaceship.controller;

import com.meroz.spaceship.controller.request.SpaceshipRequest;
import com.meroz.spaceship.controller.response.SpaceshipResponse;
import com.meroz.spaceship.service.SpaceshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/spaceships")
public class SpaceshipController {

	private final SpaceshipService spaceshipService;

	@GetMapping
	@Operation(summary = "Recover all spaceships paginated", description = "Endpoint to recover all spaceship in a specific page.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Page received", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "412", description = "Parameters validation errors", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error, support is required", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
	})
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN', 'READ_ALL')")
	public ResponseEntity<Page<SpaceshipResponse>> getAllSpaceships(Pageable page) {
		return ResponseEntity.ok(spaceshipService.getAllSpaceships(page));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Recover a spaceship by Id", description = "Endpoint to recover a spaceship for a specific Id.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Spaceship received", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "412", description = "Parameters validation errors", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error, support is required", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
	})
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<SpaceshipResponse> getSpaceshipById(@PathVariable Long id) {
		return ResponseEntity.ok(spaceshipService.getSpaceshipById(id));
	}

	@GetMapping("/name")
	@Operation(summary = "Recover all spaceships by text in name", description = "Endpoint to recover all spaceship for specific text in name.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "List found", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "412", description = "Parameters validation errors", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error, support is required", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
	})
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<List<SpaceshipResponse>> getSpaceshipByName(@RequestParam(value = "textToSearch") String textToSearch) {
		return ResponseEntity.ok(spaceshipService.getSpaceshipByContainName(textToSearch));
	}

	@PostMapping
	@Operation(summary = "Create Spaceship", description = "Endpoint to create a spaceship.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Created successfully", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "412", description = "Parameters validation errors", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error, support is required", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
	})
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'CREATE_ALL')")
	public ResponseEntity<SpaceshipResponse> createSpaceship(@RequestBody SpaceshipRequest spaceshipDto) {
		return ResponseEntity.ok(spaceshipService.createSpaceship(spaceshipDto));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Spaceship", description = "Endpoint to update spaceship by id.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Updated successfully", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "412", description = "Parameters validation errors", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error, support is required", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
	})
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<SpaceshipResponse> updateSpaceship(@PathVariable Long id, @RequestBody SpaceshipRequest spaceshipDto) {
		return ResponseEntity.ok(spaceshipService.updateSpaceship(id, spaceshipDto));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete spaceship", description = "Endpoint to delete spaceship by id.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Delete successfully", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "412", description = "Parameters validation errors", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Error, support is required", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
	})
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteSpaceship(@PathVariable Long id) {
		spaceshipService.deleteSpaceship(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/migrationByRabbit")
	@Operation(summary = "Get migrate spaceship", description = "Endpoint to get all spaceship in rabbit to migrate them.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Migration in progress", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Internal Error, support is required", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
	})
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<String> migrationByRabbit() {
		return ResponseEntity.ok(spaceshipService.createAllByRabbit());
	}
}