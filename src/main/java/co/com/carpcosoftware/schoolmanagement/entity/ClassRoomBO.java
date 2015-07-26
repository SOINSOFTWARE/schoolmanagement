/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzclassroom;

/**
 * Class room business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
@XmlRootElement(name = "classrooms")
public class ClassRoomBO extends AbstractBO implements Comparable<ClassRoomBO>,
		Serializable {

	/**
	 * Auto generated serial version
	 */
	private static final long serialVersionUID = -4200714585828342829L;

	private GradeBO gradeBO;

	private YearBO yearBO;

	private UserBO userBO;
	
	private SchoolBO schoolBO;

	public ClassRoomBO() {
		super();
	}

	public ClassRoomBO(Bzclassroom bzClassRoom) {
		super();
		this.id = bzClassRoom.getId();
		this.code = bzClassRoom.getCode();
		this.name = bzClassRoom.getName();
		this.creation = bzClassRoom.getCreation();
		this.updated = bzClassRoom.getUpdated();
		this.enabled = bzClassRoom.isEnabled();
		this.gradeBO = new GradeBO(bzClassRoom.getBzgrade());
		this.yearBO = new YearBO(bzClassRoom.getBzyear());
		this.userBO = new UserBO(bzClassRoom.getBzuser());
		this.schoolBO = new SchoolBO(bzClassRoom.getBzschool());
	}

	/**
	 * @return the grade
	 */
	public GradeBO getGradeBO() {
		return gradeBO;
	}

	/**
	 * @param gradeBO
	 *            the grade to set
	 */
	public void setGradeBO(GradeBO gradeBO) {
		this.gradeBO = gradeBO;
	}

	/**
	 * @return the year
	 */
	public YearBO getYearBO() {
		return yearBO;
	}

	/**
	 * @param yearBO
	 *            the year to set
	 */
	public void setYearBO(YearBO yearBO) {
		this.yearBO = yearBO;
	}

	/**
	 * @return the user
	 */
	public UserBO getUserBO() {
		return userBO;
	}

	/**
	 * @param userBO
	 *            the user to set
	 */
	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}
	
	/**
	 * @return the schoolBO
	 */
	public SchoolBO getSchoolBO() {
		return schoolBO;
	}

	/**
	 * @param schoolBO the schoolBO to set
	 */
	public void setSchoolBO(SchoolBO schoolBO) {
		this.schoolBO = schoolBO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((gradeBO == null) ? 0 : gradeBO.hashCode());
		result = prime * result + ((userBO == null) ? 0 : userBO.hashCode());
		result = prime * result + ((yearBO == null) ? 0 : yearBO.hashCode());
		result = prime * result + ((schoolBO == null) ? 0 : schoolBO.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassRoomBO other = (ClassRoomBO) obj;
		if (gradeBO == null) {
			if (other.gradeBO != null)
				return false;
		} else if (!gradeBO.equals(other.gradeBO))
			return false;
		if (userBO == null) {
			if (other.userBO != null)
				return false;
		} else if (!userBO.equals(other.userBO))
			return false;
		if (yearBO == null) {
			if (other.yearBO != null)
				return false;
		} else if (!yearBO.equals(other.yearBO))
			return false;
		if (schoolBO == null) {
			if (other.schoolBO != null)
				return false;
		} else if (!schoolBO.equals(other.schoolBO))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassRoomBO [gradeBO=" + gradeBO + ", yearBO=" + yearBO
				+ ", userBO=" + userBO + ", schoolBO=" + schoolBO + ", id="
				+ id + ", code=" + code + ", name=" + name + ", creation="
				+ creation + ", updated=" + updated + ", enabled=" + enabled
				+ "]";
	}

	@Override
	public int compareTo(ClassRoomBO other) {
		Integer thisGradeCode = Integer.parseInt(this.gradeBO.getCode());
		Integer otherGradeCode = Integer.parseInt(other.getGradeBO().getCode());
		return thisGradeCode.compareTo(otherGradeCode) + this.code.compareTo(other.getCode());
	}
}
