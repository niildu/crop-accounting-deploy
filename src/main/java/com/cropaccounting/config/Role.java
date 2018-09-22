package com.cropaccounting.config;

public enum Role {
	ROLE_ADMIN(1, "ADMIN"), ROLE_USER(2, "USER");
	Role(int id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

	private final int id;
	private final String roleName;

}
