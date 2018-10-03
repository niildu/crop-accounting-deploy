package com.cropaccounting.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceUnit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@PersistenceUnit(name = "default")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CropExpenceList extends ExpenceBase {
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ExpenceItemValue> expenceItemValueList = new ArrayList<ExpenceItemValue>();
}