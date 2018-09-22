package com.cropaccounting.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.cropaccounting.models.area.Block;
import com.cropaccounting.models.area.District;
import com.cropaccounting.models.area.Division;
import com.cropaccounting.models.area.SubDistrict;
import com.cropaccounting.models.area.WorkingZone;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class Farmer {
	@SequenceGenerator(name = "farmerSqGn", sequenceName = "farmerSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "farmerSqGn")
	@Id
	private long id;
	//@NotNull
	private String name;
	private String mobileNo;
	private String nid;
	//@ManyToOne
	//private Block block;
	private int block;
	private String landDagNo;
	
	private String guardianName;
	private String gender;
	private String paraName;
	private String villageName;
	private String farmerCardNo;
	private int landAmount;
	
	@ManyToOne
	private Block portalBlock;
	@ManyToOne
	private District district;
	@ManyToOne
	private Division division;
	@ManyToOne
	private SubDistrict subDistrict;
	@ManyToOne
	private WorkingZone workingZone;
	//private String blockName;
	/*
	 * @ManyToMany(cascade = {CascadeType.ALL}) public List<DocDescType>
	 * listOfDocDescType;
	 */
}
