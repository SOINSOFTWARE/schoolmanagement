package co.com.soinsoftware.schoolmanagement.entity;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzsubject;

/**
 * Subject business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 26/11/2015
 */
@XmlRootElement(name = "subjects")
public class SubjectBO extends AbstractWithCodeBO {

	private static final long serialVersionUID = -3541220834853035946L;

	public SubjectBO() {
		super();
	}

	public SubjectBO(Bzsubject bzSubject) {
		super();
		this.id = bzSubject.getId();
		this.code = bzSubject.getCode();
		this.name = bzSubject.getName();
		this.creation = bzSubject.getCreation();
		this.updated = bzSubject.getUpdated();
		this.enabled = bzSubject.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubjectBO [id=" + id + ", code=" + code + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}
