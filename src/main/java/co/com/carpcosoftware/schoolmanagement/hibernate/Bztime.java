package co.com.carpcosoftware.schoolmanagement.hibernate;

// Generated 19-abr-2015 18:35:39 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Bztime generated by hbm2java
 */
public class Bztime implements java.io.Serializable {

	private Integer id;
	private String code;
	private String name;
	private Date creation;
	private Date updated;
	private boolean enabled;
	private Set bzclassrooms = new HashSet(0);

	public Bztime() {
	}

	public Bztime(String code, String name, Date creation, Date updated,
			boolean enabled) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Bztime(String code, String name, Date creation, Date updated,
			boolean enabled, Set bzclassrooms) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.bzclassrooms = bzclassrooms;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreation() {
		return this.creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set getBzclassrooms() {
		return this.bzclassrooms;
	}

	public void setBzclassrooms(Set bzclassrooms) {
		this.bzclassrooms = bzclassrooms;
	}

}
