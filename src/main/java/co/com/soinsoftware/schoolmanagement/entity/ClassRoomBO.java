/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroom;

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

	private int idGrade;

	private GradeBO grade;

	private int idSchool;

	private SchoolBO school;

	private int idTime;

	private TimeBO time;

	private int idUser;

	private UserBO teacher;

	private int idYear;

	private YearBO year;

	private Set<ClassBO> classSet;

	private Set<UserBO> studentSet;

	public ClassRoomBO() {
		super();
	}

	public ClassRoomBO(final Bzclassroom bzClassRoom, final SchoolBO school,
			final GradeBO grade, final TimeBO time, final UserBO teacher,
			final YearBO year, final Set<ClassBO> classSet,
			final Set<UserBO> studentSet) {
		super(bzClassRoom.getId(), bzClassRoom.getCode(),
				bzClassRoom.getName(), bzClassRoom.getCreation(), bzClassRoom
						.getUpdated(), bzClassRoom.isEnabled());
		this.idGrade = grade.getId();
		this.grade = grade;
		this.idSchool = school.getId();
		this.school = school;
		this.idTime = time.getId();
		this.time = time;
		this.idUser = teacher.getId();
		this.teacher = teacher;
		this.idYear = year.getId();
		this.year = year;
		this.classSet = classSet;
		this.studentSet = studentSet;
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

	public void setGrade(final GradeBO grade) {
		this.grade = grade;
	}

	public SchoolBO getSchool() {
		return school;
	}

	public void setSchool(final SchoolBO school) {
		this.school = school;
	}

	public TimeBO getTime() {
		return time;
	}

	public void setTime(final TimeBO time) {
		this.time = time;
	}

	public UserBO getTeacher() {
		return teacher;
	}

	public void setTeacher(final UserBO user) {
		this.teacher = user;
	}

	public YearBO getYear() {
		return year;
	}

	public void setYear(final YearBO year) {
		this.year = year;
	}

	public Set<ClassBO> getClassSet() {
		return classSet;
	}

	public void setClassSet(final Set<ClassBO> classSet) {
		this.classSet = classSet;
	}

	public Set<UserBO> getStudentSet() {
		return studentSet;
	}

	public void setStudentSet(final Set<UserBO> studentSet) {
		this.studentSet = studentSet;
	}
	
	public void addStudentToStudentSet(final UserBO student) {
		if (this.studentSet == null) {
			this.studentSet = new HashSet<>();
		}
		this.studentSet.add(student);
	}

	@Override
	public String toString() {
		return "ClassRoomBO [gradeBO=" + grade.toString() + ", yearBO="
				+ year.toString() + ", timeBO=" + time.toString() + ", userBO="
				+ teacher.toString() + ", schoolBO=" + school.toString()
				+ ", id=" + id + ", code=" + code + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public int compareTo(final ClassRoomBO other) {
		final Integer thisGradeCode = Integer.parseInt(this.grade.getCode());
		final Integer otherGradeCode = Integer.parseInt(other.getGrade().getCode());
		return thisGradeCode.compareTo(otherGradeCode)
				+ this.code.compareTo(other.getCode());
	}
}