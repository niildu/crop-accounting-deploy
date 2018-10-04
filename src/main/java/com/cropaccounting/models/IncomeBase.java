package com.cropaccounting.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.experimental.Accessors;

@MappedSuperclass
@Data
@Accessors(chain = true)
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"type", "crop", "varity"})})
public class IncomeBase {
	@Id
	@GeneratedValue
	private long id;
	private String type;
	private long crop;
	private String cropName;
	private long varity;
	private String varityName;
	
	@Transient
	private String cropId;
	@Transient
	private String variety;
}
