package com.cropaccounting.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class CropIncome {
	@GeneratedValue
	@Id
	private long id;
	@NotNull
	private String name;
}
