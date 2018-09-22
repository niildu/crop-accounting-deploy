package com.cropaccounting.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class IncomeItem {
	@Id
	private long id;
	@NotNull
	@ManyToOne
	private CropIncome cropIncome;
	@NotNull
	@ManyToOne
	private CropIncomeItem cropIncomeItem;

	@Override
	public String toString() {
		return "{id:" + this.id + ",cropIncome_id:" + this.getCropIncome().getId() + ",cropIncome_name:"
				+ this.cropIncome.getName() + "," + "cropIncomeItem_id:" + this.getCropIncomeItem().getId()
				+ ",cropIncomeItem_name:" + this.cropIncomeItem.getName() + "}";
	}

	public String toStringJson() {
		return "{\"id\":\"" + this.id + "\",\"cropIncome_id\":\"" + this.getCropIncome().getId()
				+ "\",\"cropIncome_name\":\"" + this.cropIncome.getName() + "\"," + "\"cropIncomeItem_id\":\""
				+ this.getCropIncomeItem().getId() + "\",\"cropIncomeItem_name\":\"" + this.cropIncomeItem.getName()
				+ "\"" + "}";
	}
}
