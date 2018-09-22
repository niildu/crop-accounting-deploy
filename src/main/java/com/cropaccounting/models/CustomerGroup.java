package com.cropaccounting.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@PersistenceUnit(name = "default")
@Data
public class CustomerGroup {
	@Id
	private long id;
	@NotNull(message = "Group Id must be unique")
	@Column(unique = true)
	private long groupId;
	@NotNull(message = "Group Name must be unique")
	@Column(unique = true)
	private String groupName;
}
