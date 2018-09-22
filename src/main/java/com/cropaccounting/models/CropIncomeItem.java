package com.cropaccounting.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class CropIncomeItem {
	@Id
	private long id;
	@NotNull
	private String name;
}
