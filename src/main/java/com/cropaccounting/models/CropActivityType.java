package com.cropaccounting.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.PersistenceUnit;
import javax.persistence.Column;

import lombok.Data;
import lombok.NonNull;

@Entity
@PersistenceUnit(name = "default")
@Table(name="crop_activity_type")
@Data
public class CropActivityType {
	@SequenceGenerator(name = "cropActivityTypeSqGn", sequenceName = "cropActivityTypeSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "cropActivityTypeSqGn")
	@Id
	private long id;
	//@NonNull
	@Column(name = "name")
	private String name;
}
