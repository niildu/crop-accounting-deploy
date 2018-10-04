package com.cropaccounting.models.area;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cropaccounting.models.IncomeBase;
import com.cropaccounting.models.IncomeItemValue;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"division_id", "district_id", "sub_district_id"})})
public class AreaCropIncome extends IncomeBase {
	@ManyToMany(cascade = CascadeType.ALL)
	private List<IncomeItemValue> areaIncomeItemValueList = new ArrayList<IncomeItemValue>();
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
}
