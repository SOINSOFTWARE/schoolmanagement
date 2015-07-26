/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.entity;

import java.util.Date;
import java.util.Objects;

/**
 * Abstract object
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 12/03/2015
 */
public abstract class AbstractBO {
	
	/**
	 * Identifier
	 */
	protected Integer id;

	/**
	 * Code
	 */
	protected String code;

	/**
	 * Name
	 */
	protected String name;

	/**
	 * Creation date
	 */
	protected Date creation;

	/**
	 * Last update date
	 */
	protected Date updated;

	/**
	 * Enabled
	 */
	protected boolean enabled;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the creation
	 */
	public Date getCreation() {
		return creation;
	}

	/**
	 * @param creation the creation to set
	 */
	public void setCreation(Date creation) {
		this.creation = creation;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 89 * hash + Objects.hashCode(this.id);
		hash = 89 * hash + Objects.hashCode(this.code);
		hash = 89 * hash + Objects.hashCode(this.name);
		hash = 89 * hash + Objects.hashCode(this.creation);
		hash = 89 * hash + Objects.hashCode(this.updated);
		hash = 89 * hash + (this.enabled ? 1 : 0);
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
		if (!Objects.equals(this.code, other.code)) {
			return false;
		}
		return Objects.equals(this.name, other.name);
	}
}
