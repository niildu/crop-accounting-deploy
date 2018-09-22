package com.cropaccounting.models.area;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Division {
	@GeneratedValue
	@Id
	private long id;
	private String name;
	private long portalId;
}
