/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzclass;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroom;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroomxuser;

/**
 * Class room business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
@XmlRootElement(name = "classrooms")
public class ClassRoomBO extends AbstractWithCodeBO implements
		Comparable<ClassRoomBO> {

	private static final long serialVersionUID = -4200714585828342829L;

	private GradeBO grade;

	private SchoolBO school;

	private TimeBO time;

	private UserBO teacher;

	private YearBO year;

	private int idGrade;

	private int idSchool;

	private int idTime;

	private int idUser;

	private int idYear;

	private Set<ClassBO> classSet;

	private Set<UserBO> studentSet;

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
		this.grade = new GradeBO(bzClassRoom.getBzgrade());
		this.school = new SchoolBO(bzClassRoom.getBzschool());
		this.time = new TimeBO(bzClassRoom.getBztime());
		this.teacher = new UserBO(bzClassRoom.getBzuser());
		this.year = new YearBO(bzClassRoom.getBzyear());

		Set<?> bzClassSet = bzClassRoom.getBzclasses();
		if (bzClassSet != null && !bzClassSet.isEmpty()) {
			this.classSet = new HashSet<>();
			bzClassSet.stream().forEach((bzClass) -> {
				classSet.add(new ClassBO(((Bzclass) bzClass)));
			});
		}

		Set<?> bzClassRoomXUserSet = bzClassRoom.getBzclassroomxusers();
		if (bzClassRoomXUserSet != null && !bzClassRoomXUserSet.isEmpty()) {
			this.studentSet = new HashSet<>();
			bzClassRoomXUserSet.stream().forEach(
					(bzClassRoomXUser) -> {
						studentSet.add(new UserBO(
								((Bzclassroomxuser) bzClassRoomXUser)
										.getBzuser()));
					});
		}
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

	public GradeBO getGrade() {
		return grade;
	}

	public void setGrade(GradeBO grade) {
		this.grade = grade;
	}

	public SchoolBO getSchool() {
		return school;
	}

	public void setSchool(SchoolBO school) {
		this.school = school;
	}

	public TimeBO getTime() {
		return time;
	}

	public void setTime(TimeBO time) {
		this.time = time;
	}

	public UserBO getTeacher() {
		return teacher;
	}

	public void setTeacher(UserBO user) {
		this.teacher = user;
	}

	public YearBO getYear() {
		return year;
	}

	public void setYear(YearBO year) {
		this.year = year;
	}

	public Set<ClassBO> getClassSet() {
		return classSet;
	}

	public void setClassSet(Set<ClassBO> classSet) {
		this.classSet = classSet;
	}

	public Set<UserBO> getStudentSet() {
		return studentSet;
	}

	public void setStudentSet(Set<UserBO> studentSet) {
		this.studentSet = studentSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClassRoomBO [gradeBO=" + grade.toString() + ", yearBO="
				+ year.toString() + ", timeBO=" + time.toString()
				+ ", userBO=" + teacher.toString() + ", schoolBO="
				+ school.toString() + ", id=" + id + ", code=" + code
				+ ", name=" + name + ", creation=" + creation + ", updated="
				+ updated + ", enabled=" + enabled + "]";
	}

	@Override
	public int compareTo(ClassRoomBO other) {
		Integer thisGradeCode = Integer.parseInt(this.grade.getCode());
		Integer otherGradeCode = Integer.parseInt(other.getGrade().getCode());
		return thisGradeCode.compareTo(otherGradeCode)
				+ this.code.compareTo(other.getCode());
	}
}