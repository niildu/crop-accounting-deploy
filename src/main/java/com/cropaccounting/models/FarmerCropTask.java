package com.cropaccounting.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class FarmerCropTask {
	@SequenceGenerator(name = "farmerCropTaskSqGn", sequenceName = "farmerCropTaskSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "farmerCropTaskSqGn")
	@Id
	private long id;
	private String type;
	
	private long crop;
	private String cropName;
	private long varity;
	private String varityName;
	private LocalDate startDate;
	@ManyToOne(cascade = CascadeType.ALL)
	private Farmer farmer;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<FarmerTask> farmerTaskList = new ArrayList<FarmerTask>();
}
