package com.cropaccounting.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
//@PersistenceUnit(name = "other")
@PersistenceUnit(name = "default")
@Table(name="crops")
@Data
public class Crops {
	@SequenceGenerator(name = "cropsSqGn", sequenceName = "cropsSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "cropsSqGn")
	@Id
	private long id;
	//@NotNull
	@Column(name = "name")
	private String name;	
	@Column(name = "type")
	private String type;	
	@Column(name = "image")
	private String image;	
}



