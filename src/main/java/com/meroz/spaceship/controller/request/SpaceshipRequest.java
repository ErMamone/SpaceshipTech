package com.meroz.spaceship.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpaceshipRequest {

	private Long id;

	private String name;

	private String series;

	private String manufacturer;
}
