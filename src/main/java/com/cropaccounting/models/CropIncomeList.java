package com.cropaccounting.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceUnit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@PersistenceUnit(name = "default")
@Data
@EqualsAndHashCode(callSuper = false)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CropIncomeList extends IncomeBase {
	//@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@ManyToMany(cascade = CascadeType.ALL)
	private List<IncomeItemValue> incomeItemValueList = new ArrayList<IncomeItemValue>();
}
