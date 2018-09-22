package com.cropaccounting.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class CropTaskMap {
	@SequenceGenerator(name = "cropTaskMapSqGn", sequenceName = "cropTaskMapSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "cropTaskMapSqGn")
	@Id
	private long id;
	private String taskDesk = null;
	private String type;
	private long crop;
	private String cropName;
	private long varity;
	private String varityName;
	@ManyToMany(cascade=CascadeType.ALL)
	private List<CropCalenderTask> taskList = new ArrayList<CropCalenderTask>();
	
	public CropCalenderTask getTask(CropActivityType cropActivityType)
	{
		return taskList.stream().filter(task -> task.getCropActivityType().getId() == cropActivityType.getId())
				.findFirst().orElse(null);
	}
}
