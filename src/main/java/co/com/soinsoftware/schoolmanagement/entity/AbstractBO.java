/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Abstract object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 12/03/2015
 */
public abstract class AbstractBO implements Serializable {

	private static final long serialVersionUID = 2604161321945045561L;

	protected Integer id;

	protected String name;

	protected Date creation;

	protected Date updated;

	protected boolean enabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 89 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractBO other = (AbstractBO) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
}
