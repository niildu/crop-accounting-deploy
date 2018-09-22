package com.cropaccounting.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "auth-config")
@Data
public class SpringSecurityConfigUserList {
	private List<User> users = new ArrayList<>();

	@Data
	public static class User {
		private String code;
		private String password;
		private String[] roles;
	}
}
