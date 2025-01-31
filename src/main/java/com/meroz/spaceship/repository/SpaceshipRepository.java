package com.meroz.spaceship.repository;

import com.meroz.spaceship.entities.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
	Optional<Spaceship> findByNameContains(String name);
}
