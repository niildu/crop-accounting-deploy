package com.cropaccounting.models;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;

import com.cropaccounting.util.LocalDateTimeAttributeConverter;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class CropCalenderTask {
	@SequenceGenerator(name = "cropcalenderTaskSqGn", sequenceName = "cropcalenderTaskSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "cropcalenderTaskSqGn")
	@Id
	private long id;
	private String taskName;
	private String taskDateStr;
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime dateOfUpload;

	@ManyToOne
	private CropActivity cropActivity;

	@ManyToOne
	private CropActivityType cropActivityType;

	private String comments;
}
