package com.cropaccounting.models.area;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.cropaccounting.models.ExpenceBase;
import com.cropaccounting.models.ExpenceItemValue;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class AreaCropExpence extends ExpenceBase {
	@GeneratedValue
	@Id
	private long id;
	@ManyToOne
	private Block block;
	@ManyToOne
	private District district;
	@ManyToOne
	private Division division;
	@ManyToOne
	private SubDistrict subDistrict;
	@ManyToOne
	private WorkingZone workingZone;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ExpenceItemValue> areaExpenceItemValueList = new ArrayList<ExpenceItemValue>();
}
