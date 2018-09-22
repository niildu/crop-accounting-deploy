package com.cropaccounting.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class ExpenceItemValue {
	@GeneratedValue
	@Id
	private long id;
	@ManyToOne
	private CropCalenderTask cropCalenderTask;
	@ManyToOne
	private CropActivityItem cropActivityItem;

	private BigDecimal itemExpence;
	private BigDecimal labourExpence;

	@Override
	public String toString() {
		return "{id:" + this.id + ",cropActivityItem:" + this.getCropActivityItem().getId() + ",cropActivityItemname:"
				+ this.cropActivityItem.getName() + "," + "itemExpence:" + this.itemExpence + ",labourExpence:"
				+ this.labourExpence + "}";
	}

	public CropActivityType getCropActivityType(List<ExpenceItem> expenceItemList) {
		return expenceItemList.stream()
				.filter(expenceItem -> expenceItem.getCropActivityItem().getId() == this.getCropActivityItem().getId())
				.findFirst().map(ExpenceItem::getCropActivityType).orElse(null);
	}

	public CropActivity getCropActivity(List<ExpenceItem> expenceItemList) {
		return expenceItemList.stream()
				.filter(expenceItem -> expenceItem.getCropActivityItem().getId() == this.getCropActivityItem().getId())
				.findFirst().map(ExpenceItem::getCropActivity).orElse(null);
	}
}
