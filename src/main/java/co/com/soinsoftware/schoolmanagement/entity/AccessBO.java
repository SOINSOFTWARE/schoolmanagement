/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Cnaccess;

/**
 * Access business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 24/03/2015
 */
@XmlRootElement(name = "access")
public class AccessBO extends AbstractWithCodeBO {

	private static final long serialVersionUID = -5197649029321484212L;

	public AccessBO() {
		super();
	}

	public AccessBO(Cnaccess cnAccess) {
		super();
		this.id = cnAccess.getId();
		this.code = cnAccess.getCode();
		this.name = cnAccess.getName();
		this.creation = cnAccess.getCreation();
		this.updated = cnAccess.getUpdated();
		this.enabled = cnAccess.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccessBO [id=" + id + ", code=" + code + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}