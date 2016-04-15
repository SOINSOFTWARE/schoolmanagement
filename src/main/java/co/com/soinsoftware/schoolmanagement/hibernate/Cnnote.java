package co.com.soinsoftware.schoolmanagement.hibernate;

// Generated 15-Apr-2016 16:37:40 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Cnnote generated by hbm2java
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class Cnnote implements java.io.Serializable {

	private Integer id;
	private String code;
	private String name;
	private Date creation;
	private Date updated;
	private boolean enabled;
	private Set cnnotevalues = new HashSet(0);

	public Cnnote() {
	}

	public Cnnote(String code, String name, Date creation, Date updated,
			boolean enabled) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}
	
	public Cnnote(String code, String name, Date creation, Date updated,
			boolean enabled, Set cnnotevalues) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.cnnotevalues = cnnotevalues;
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

	public Set getCnnotevalues() {
		return cnnotevalues;
	}

	public void setCnnotevalues(Set cnnotevalues) {
		this.cnnotevalues = cnnotevalues;
	}

}