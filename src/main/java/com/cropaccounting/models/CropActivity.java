package com.cropaccounting.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.PersistenceUnit;

import lombok.Data;
import lombok.NonNull;

@Entity
@PersistenceUnit(name = "default")
@Data
public class CropActivity {
	@SequenceGenerator(name = "cropActivitySqGn", sequenceName = "cropActivitySeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "cropActivitySqGn")
	@Id
	private long id;
	//@NonNull
	private String name;
}
