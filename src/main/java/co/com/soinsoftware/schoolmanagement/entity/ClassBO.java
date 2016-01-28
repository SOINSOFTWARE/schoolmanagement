/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzclass;
import co.com.soinsoftware.schoolmanagement.hibernate.Bznotedefinition;

/**
 * Class business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 26/11/2015
 */
@XmlRootElement(name = "classes")
public class ClassBO extends AbstractBO {

	private static final long serialVersionUID = -39758306494005636L;
	
	private int idClassRoom;
	
	private int idSubject;
	
	private int idTeacher;

	private ClassRoomBO classRoom;

	private SubjectBO subject;

	private UserBO teacher;

	private Set<NoteDefinitionBO> noteDefinitionSet;

	public ClassBO() {
		super();
	}

	public ClassBO(Bzclass bzClass) {
		super();
		this.id = bzClass.getId();
		this.name = bzClass.getName();
		this.classRoom = (bzClass.getBzclassroom() != null) ? new ClassRoomBO(
				bzClass.getBzclassroom()) : null;
		this.subject = (bzClass.getBzsubject() != null) ? new SubjectBO(
				bzClass.getBzsubject()) : null;
		this.teacher = (bzClass.getBzuser() != null) ? new UserBO(
				bzClass.getBzuser()) : null;
		this.creation = bzClass.getCreation();
		this.updated = bzClass.getUpdated();
		this.enabled = bzClass.isEnabled();

		Set<?> bzNoteDefSet = bzClass.getBznotedefinitions();
		if (bzNoteDefSet != null && !bzNoteDefSet.isEmpty()) {
			this.noteDefinitionSet = new HashSet<>();
			bzNoteDefSet.stream().forEach(
					(bzNoteDef) -> {
						this.noteDefinitionSet.add(new NoteDefinitionBO(
								((Bznotedefinition) bzNoteDef)));
					});
		}
	}

	public ClassRoomBO getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoomBO classRoom) {
		this.classRoom = classRoom;
	}

	public SubjectBO getSubject() {
		return subject;
	}

	public void setSubject(SubjectBO subject) {
		this.subject = subject;
	}

	public UserBO getTeacher() {
		return teacher;
	}

	public void setTeacher(UserBO teacher) {
		this.teacher = teacher;
	}

	public Set<NoteDefinitionBO> getNoteDefinitionSet() {
		return noteDefinitionSet;
	}

	public void setNoteDefinitionSet(Set<NoteDefinitionBO> noteDefinitionSet) {
		this.noteDefinitionSet = noteDefinitionSet;
	}

	public int getIdClassRoom() {
		return idClassRoom;
	}

	public void setIdClassRoom(int idClassRoom) {
		this.idClassRoom = idClassRoom;
	}

	public int getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(int idSubject) {
		this.idSubject = idSubject;
	}

	public int getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(int idTeacher) {
		this.idTeacher = idTeacher;
	}

	@Override
	public String toString() {
		return "ClassBO [subject=" + subject + ", teacher=" + teacher
				+ ", noteDefinitionSet=" + noteDefinitionSet + ", id=" + id
				+ ", name=" + name + ", creation=" + creation + ", updated="
				+ updated + ", enabled=" + enabled + "]";
	}
}