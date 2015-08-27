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
    
    private SchoolBO schoolBO;
    
    private TimeBO timeBO;
    
    private UserBO userBO;
    
    private YearBO yearBO;
    
    private int idGrade;
    
    private int idSchool;
    
    private int idTime;
    
    private int idUser;    
    
    private int idYear;    

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
		this.idGrade = bzClassRoom.getBzgrade().getId();
		this.idSchool = bzClassRoom.getBzschool().getId();
		this.idTime = bzClassRoom.getBztime().getId();
		this.idUser = bzClassRoom.getBzuser().getId();
		this.idYear = bzClassRoom.getBzyear().getId();
		this.gradeBO = new GradeBO(bzClassRoom.getBzgrade());
		this.schoolBO = new SchoolBO(bzClassRoom.getBzschool());
		this.timeBO = new TimeBO(bzClassRoom.getBztime());
		this.userBO = new UserBO(bzClassRoom.getBzuser());
		this.yearBO = new YearBO(bzClassRoom.getBzyear());
	}
	
	public int getIdGrade() {
		return this.idGrade;
	}
	
	public int getIdSchool() {
		return this.idSchool;
	}
	
	public int getIdTime() {
		return this.idTime;
	}
	
	public int getIdUser() {
		return this.idUser;
	}
	
	public int getIdYear() {
		return this.idYear;
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
	
	public TimeBO getTimeBO() {
		return timeBO;
	}

	public void setTimeBO(TimeBO timeBO) {
		this.timeBO = timeBO;
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
		result = prime * result + ((timeBO == null) ? 0 : timeBO.hashCode());
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
		if (timeBO == null) {
			if (other.timeBO != null)
				return false;
		} else if (!timeBO.equals(other.timeBO))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassRoomBO [gradeBO=" + gradeBO + ", yearBO=" + yearBO + ", timeBO=" + timeBO
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
