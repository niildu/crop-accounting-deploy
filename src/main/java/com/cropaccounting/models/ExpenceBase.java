package com.cropaccounting.models;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class ExpenceBase {
	@GeneratedValue
	@Id
	private long id;
	private String type;
	private long crop;
	private long varity;
	@ManyToOne(cascade = CascadeType.ALL)
	private CropTaskMap cropTaskMap;
}
