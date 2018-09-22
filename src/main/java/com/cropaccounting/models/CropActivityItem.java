package com.cropaccounting.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Table(name="crop_activity_item")
@Data
public class CropActivityItem {
	@SequenceGenerator(name = "cropActivityItemSqGn", sequenceName = "cropActivityItemSeq", initialValue = 155, allocationSize = 100)
	@GeneratedValue(generator = "cropActivityItemSqGn")
	@Id
	private long id;
	//@NonNull
	@Column(name = "name")
	private String name;
}
