package com.cropaccounting.models.area;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class District {
	@GeneratedValue
	@Id
	private long id;
	private String name;
	private long portalId;
	
	@ManyToOne
	private Division division;
}
