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
	
	public AbstractBO() {
		super();
	}

	public AbstractBO(final Integer id, final String name, final Date creation,
			final Date updated, final boolean enabled) {
		this();
		this.id = id;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(final Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 89 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
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