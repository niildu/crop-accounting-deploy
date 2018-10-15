package com.cropaccounting.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceUnit;

import com.cropaccounting.models.area.AreaCropIncome;

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
	
	public Optional<AreaCropIncome> getByMasterData(CropIncomeList masterData, List<AreaCropIncome> areaData) {
		return areaData.stream().filter(data -> data.getCropId() == masterData.getCropId() 
				&& data.getVarity() == masterData.getVarity()).findFirst();
	}
}
