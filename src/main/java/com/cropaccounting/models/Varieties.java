package com.cropaccounting.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
//@PersistenceUnit(name = "other")
@PersistenceUnit(name = "default")
@Table(name="varieties")
@Data
public class Varieties {
	@SequenceGenerator(name = "varitySqGn", sequenceName = "varietySeq", initialValue = 28, allocationSize = 100)
	@GeneratedValue(generator = "varitySqGn")
	@Id
	private long id;
	//@Column(name = "name")
	private String name;	
	//@Column(name = "crop_id")
	private long crop_id;	
}



