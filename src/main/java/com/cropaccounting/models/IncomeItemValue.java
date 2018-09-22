package com.cropaccounting.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class IncomeItemValue {
	@GeneratedValue
	@Id
	private long id;
	@ManyToOne
	private CropIncome cropIncome;
	private String type;
	private int day;
	private BigDecimal amount;
	private BigDecimal totValue;

	@Override
	public String toString() {
		return "{id:" + this.id + ",cropIncome:" + this.cropIncome.getId() + ",type:" + this.cropIncome.getName() + "," + "day:"
				+ this.day + ",amount:" + this.amount + ",totValue:" + totValue + "}";
	}
}
