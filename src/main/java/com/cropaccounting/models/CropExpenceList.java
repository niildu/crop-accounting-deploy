package com.cropaccounting.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceUnit;

import com.cropaccounting.models.area.AreaCropExpence;

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

	public Optional<AreaCropExpence> getByMasterData(CropExpenceList masterData, List<AreaCropExpence> areaData) {
		return areaData.stream().filter(data -> data.getCrop() == masterData.getCrop() 
				&& data.getVarity() == masterData.getVarity()).findFirst();
	}
}