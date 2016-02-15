/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.util.Date;

/**
 * Abstract object that provides code column necessary for multiple BO objects
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 26/11/2015
 */
public abstract class AbstractWithCodeBO extends AbstractBO {

	private static final long serialVersionUID = -2024771930579614306L;

	protected String code;
	
	public AbstractWithCodeBO() {
		super();
	}

	public AbstractWithCodeBO(final Integer id, final String name,
			final Date creation, final Date updated, final boolean enabled) {
		super(id, name, creation, updated, enabled);
	}

	public AbstractWithCodeBO(final Integer id, final String code,
			final String name, final Date creation, final Date updated,
			final boolean enabled) {
		super(id, name, creation, updated, enabled);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "AbstractWithCodeBO [code=" + code + ", id=" + id + ", name="
				+ name + ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}