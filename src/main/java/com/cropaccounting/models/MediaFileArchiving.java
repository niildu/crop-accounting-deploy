package com.cropaccounting.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class MediaFileArchiving {
	/* Work Register Form Fields */
	@Id
	private long id;
	@NotNull
	private Date dateOfUpload;
	@NotNull
	private String typeOfMedia;
	@NotNull
	private String description;
	@NotNull
	private String searchKeyward;
	private String uploadedFile;
	private String accessLink;
}
