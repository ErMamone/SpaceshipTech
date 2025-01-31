package com.meroz.spaceship.controller.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpaceshipResponse {

	private Long id;

	private String name;

	private String series;

	private String manufacturer;
}
