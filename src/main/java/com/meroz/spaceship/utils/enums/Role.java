package com.meroz.spaceship.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
	ROLE_ADMIN(Arrays.asList(
			RolePermission.CREATE_ALL,
			RolePermission.READ_ALL,
			RolePermission.UPDATE_ALL,
			RolePermission.DISABLE_ALL)),
	ROLE_USER(Arrays.asList(
			RolePermission.CREATE_USER,
			RolePermission.READ_USER,
			RolePermission.UPDATE_USER));

	private final List<RolePermission> permissions;


}
