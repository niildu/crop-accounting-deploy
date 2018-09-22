package com.cropaccounting.models;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

//@Entity
//@PersistenceUnit(name = "default")
@Data
public class PortalCrop {
	@Id
	private long id;
	@NotNull
	private String name;
	@NotNull
	private String customerId;
	@NotNull
	private long portalid;
	private String accountId;
	private String groupId;
	private String agreementId;
	private String description;
	@NotNull
	private String locations;
	/*
	 * @ManyToMany(cascade = {CascadeType.ALL}) private List<DocDescType>
	 * listOfDocDescType;
	 */
}
