package com.cropaccounting.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.NotNull;
import javax.persistence.Table;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Table(name="expence_item")
@Data
public class ExpenceItem {
	@SequenceGenerator(name = "expenceSqGn", sequenceName = "expenceSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "expenceSqGn")
	@Id
	private long id;
	//@NotNull
	@ManyToOne
	public CropActivity cropActivity;
	//@NotNull
	@ManyToOne
	public CropActivityType cropActivityType;
	@ManyToOne
	public CropActivityItem cropActivityItem;

	@Override
	public String toString() {
		return "{id:" + this.id + ",cropActivity_id:" + this.cropActivity.getId() + ",cropActivity_name:"
				+ this.cropActivity.getName() + "," + "cropActivityType_id:" + this.cropActivityType.getId()
				+ ",cropActivityType_name:" + this.cropActivityType.getName() + "," + "cropActivityItem_id:"
				+ this.cropActivityItem.getId() + ",cropActivityItem_name:" + this.cropActivityItem.getName() + "}";
	}

	public String toStringJson() {
		return "'{\"id\":\"" + this.id + "\",\"cropActivity_id\":\"" + this.cropActivity.getId()
				+ "\",\"cropActivity_name\":\"" + this.cropActivity.getName() + "\"," + "\"cropActivityType_id\":\""
				+ this.cropActivityType.getId() + "\",\"cropActivityType_name\":\"" + this.cropActivityType.getName()
				+ "\"," + "\"cropActivityItem_id\":\"" + this.cropActivityItem.getId()
				+ "\",\"cropActivityItem_name\":\"" + this.cropActivityItem.getName() + "\"" + "}'";
	}
}
