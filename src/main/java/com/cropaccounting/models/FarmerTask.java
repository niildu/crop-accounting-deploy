package com.cropaccounting.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Convert;
import com.cropaccounting.util.LocalDateTimeAttributeConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Entity
@PersistenceUnit(name = "default")
@Table(name = "farmer_task")
@Data @Wither @AllArgsConstructor @NoArgsConstructor
public class FarmerTask {
	@SequenceGenerator(name = "farmerTaskSqGn", sequenceName = "farmerTaskSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "farmerTaskSqGn")
	@Id
	private long id;
	@ManyToOne
	private CropActivity cropActivity;
	@ManyToOne
	private CropActivityType cropActivityType;
	@ManyToOne
	private CropActivityItem cropActivityItem;

	private BigDecimal itemExpence;
	private BigDecimal labourExpence;
	private String taskName;
	private String taskDateStr;
	
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime dateOfUpload;
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime startDate;
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime endDate;

	/*public FarmerTask(CropActivity cropActivity, CropActivityType cropActivityType, CropActivityItem cropActivityItem,
			float itemExpence, float labourExpence, String taskName, String taskDateStr, LocalDateTime dateOfUpload,
			LocalDateTime start, LocalDateTime end) {
		this.cropActivity = cropActivity;
		this.cropActivityType = cropActivityType;
		this.cropActivityItem = cropActivityItem;

		this.itemExpence = itemExpence;
		this.labourExpence = labourExpence;
		this.taskName = taskName;
		this.taskDateStr = taskDateStr;
		this.dateOfUpload = dateOfUpload;
		this.start = start;
		this.end = end;
	}*/

	@Override
	public String toString() {
		return "{id:'',cropActivity_id:'',cropActivity_name:" + this.cropActivity.getName() + ","
				+ "cropActivityType_id:'',cropActivityType_name:" + this.cropActivityType.getName() + ","
				+ "cropActivityItem_id:'',cropActivityItem_name:" + this.cropActivityItem.getName() + ","
				+ "itemExpence:" + this.itemExpence + ", labourExpence:" + this.labourExpence + "," + "taskName:"
				+ this.taskName + ", taskDateStr:" + this.taskDateStr + "}";
	}
}
