package co.com.soinsoftware.schoolmanagement.entity;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzgrade;

/**
 * Grade business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
@XmlRootElement(name = "grades")
public class GradeBO extends AbstractWithCodeBO implements Comparable<GradeBO> {

	private static final long serialVersionUID = 5622827774843357933L;

	public GradeBO() {
		super();
	}

	public GradeBO(Bzgrade bzGrade) {
		super();
		this.id = bzGrade.getId();
		this.code = bzGrade.getCode();
		this.name = bzGrade.getName();
		this.creation = bzGrade.getCreation();
		this.updated = bzGrade.getUpdated();
		this.enabled = bzGrade.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GradeBO [id=" + id + ", code=" + code + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public int compareTo(GradeBO other) {
		Integer thisCode = Integer.parseInt(this.code);
		Integer otherCode = Integer.parseInt(other.getCode());
		return thisCode.compareTo(otherCode);
	}
}