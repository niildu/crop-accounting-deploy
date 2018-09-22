package com.cropaccounting.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Role Model - Contain ACL Role data.
 */

@Entity
@PersistenceUnit(name = "default")
@Data
public class Role /* extends Model implements models.deadbolt.Role */ {
	@Id
	private long id;
	@NotNull(message = "Role Name must be unique")
	@Column(unique = true)
	public String name;

	public Role(String name) {
		this.name = name;
	}

	/*
	 * public static Role findByName(String name) { return Role.find("byName",
	 * name).first(); }
	 * 
	 * // From Role Interface
	 * 
	 * @Override public String getRoleName() { return this.name; }
	 */

	/** return AC role */
	/*
	 * public static Role getEnumerator() { return Role.findByName("Enumerator"); }
	 * 
	 *//** return FS role */
	/*
	 * public static Role getFSRole() { return Role.findByName("FS"); }
	 * 
	 *//** return AC role */
	/*
	 * public static Role getACRole() { return Role.findByName("AC"); }
	 * 
	 *//** return TLI role */
	/*
	 * public static Role getTLIRole() { return Role.findByName("TLI"); }
	 * 
	 *//** return FD role */
	/*
	 * public static Role getFDRole() { return Role.findByName("FD"); }
	 * 
	 *//** return parent role from hierarchy *//*
												 * public static Role getParent(Role role) { Role parentRole = null; if
												 * (role.equals(Role.getFSRole())) { parentRole = null; } else if
												 * (role.equals(Role.getACRole())) { parentRole = Role.getFSRole(); }
												 * else if (role.equals(Role.getTLIRole())) { parentRole =
												 * Role.getACRole(); } else if (role.equals(Role.getFDRole())) {
												 * parentRole = Role.getTLIRole(); }
												 * 
												 * return parentRole; }
												 */

}
