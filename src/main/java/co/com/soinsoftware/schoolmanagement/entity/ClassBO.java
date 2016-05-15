/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzclass;

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
	
	private ClassRoomBO classRoom;

	private int idSubject;
	
	private SubjectBO subject;

	private int idTeacher;

	private UserBO teacher;

	private Set<NoteDefinitionBO> noteDefinitionSet;
	
	private volatile FinalNoteBO finalNote;
	
	private volatile String qualitativeNote;
	
	private volatile String achievements;

	public ClassBO() {
		super();
	}

	public ClassBO(final Bzclass bzClass, final ClassRoomBO classRoom, final SubjectBO subject,
			final UserBO teacher, final Set<NoteDefinitionBO> noteDefinitionSet) {
		super(bzClass.getId(), bzClass.getName(), bzClass.getCreation(),
				bzClass.getUpdated(), bzClass.isEnabled());
		this.classRoom = classRoom;
		this.subject = subject;
		this.teacher = teacher;
		this.noteDefinitionSet = noteDefinitionSet;
	}

	public ClassRoomBO getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(final ClassRoomBO classRoom) {
		this.classRoom = classRoom;
	}

	public SubjectBO getSubject() {
		return subject;
	}

	public void setSubject(final SubjectBO subject) {
		this.subject = subject;
	}

	public UserBO getTeacher() {
		return teacher;
	}

	public void setTeacher(final UserBO teacher) {
		this.teacher = teacher;
	}

	public Set<NoteDefinitionBO> getNoteDefinitionSet() {
		return noteDefinitionSet;
	}

	public void setNoteDefinitionSet(
			final Set<NoteDefinitionBO> noteDefinitionSet) {
		this.noteDefinitionSet = noteDefinitionSet;
	}

	public int getIdClassRoom() {
		return idClassRoom;
	}

	public void setIdClassRoom(final int idClassRoom) {
		this.idClassRoom = idClassRoom;
	}

	public int getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(final int idSubject) {
		this.idSubject = idSubject;
	}

	public int getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(final int idTeacher) {
		this.idTeacher = idTeacher;
	}

	public FinalNoteBO getFinalNote() {
		return finalNote;
	}

	public void setFinalNote(FinalNoteBO finalNote) {
		this.finalNote = finalNote;
	}

	public String getQualitativeNote() {
		return qualitativeNote;
	}

	public void setQualitativeNote(String qualitativeNote) {
		this.qualitativeNote = qualitativeNote;
	}

	public String getAchievements() {
		return achievements;
	}

	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}

	@Override
	public String toString() {
		return "ClassBO [subject=" + subject + ", teacher=" + teacher
				+ ", noteDefinitionSet=" + noteDefinitionSet + ", id=" + id
				+ ", name=" + name + ", creation=" + creation + ", updated="
				+ updated + ", enabled=" + enabled + "]";
	}
}