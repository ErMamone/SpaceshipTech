package com.meroz.spaceship.repository;

import com.meroz.spaceship.entities.Spaceship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceshipRepository extends PagingAndSortingRepository<Spaceship, Long>, CrudRepository<Spaceship, Long> {


	List<Spaceship> findSpaceshipsByNameContains(String name);

}
