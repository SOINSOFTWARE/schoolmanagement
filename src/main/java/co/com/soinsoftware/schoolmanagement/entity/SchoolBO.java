/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzschool;

/**
 * School Business object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
@XmlRootElement(name="schools")
public class SchoolBO extends AbstractWithCodeBO {

	private static final long serialVersionUID = -318874995739938197L;
	
	public SchoolBO() {
		super();
	}

	public SchoolBO(Bzschool bzSchool) {
		super();
		this.id = bzSchool.getId();
		this.code = bzSchool.getCode();
		this.name = bzSchool.getName();
		this.creation = bzSchool.getCreation();
		this.updated = bzSchool.getUpdated();
		this.enabled = bzSchool.isEnabled();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SchoolBO [id=" + id + ", code=" + code + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}