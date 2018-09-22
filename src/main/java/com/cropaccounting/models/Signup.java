package com.cropaccounting.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Signup {
	@GeneratedValue
	@Id
	private long id;
	private String name;
	private long portalId;
	private String email;
	private String password;
}
