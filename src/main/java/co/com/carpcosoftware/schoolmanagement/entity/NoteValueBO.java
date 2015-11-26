package co.com.carpcosoftware.schoolmanagement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bznotevalue;

/**
 * Note value business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 26/11/2015
 */
@XmlRootElement(name = "notevalues")
public class NoteValueBO implements Serializable {

	private static final long serialVersionUID = -1294581087156310419L;
	
	private int idStudent;
	
	private UserBO student;
	
	private int idNoteDefinition;
	
	private NoteDefinitionBO noteDefinition;
	
	private BigDecimal value;
	
	private Date creation;

	private Date updated;

	private boolean enabled;
	
	public NoteValueBO() {
		super();
	}
	
	public NoteValueBO(Bznotevalue bzNoteValue) {
		super();
		if (bzNoteValue.getId() != null) {
			this.idStudent =  bzNoteValue.getId().getIdUser();
			this.idNoteDefinition = bzNoteValue.getId().getIdNoteDefinition();
		}
		this.value = bzNoteValue.getValue();
		this.creation = bzNoteValue.getCreation();
		this.updated = bzNoteValue.getUpdated();
		this.enabled = bzNoteValue.isEnabled();
	}

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	public UserBO getStudent() {
		return student;
	}

	public void setStudent(UserBO student) {
		this.student = student;
	}

	public int getIdNoteDefinition() {
		return idNoteDefinition;
	}

	public void setIdNoteDefinition(int idNoteDefinition) {
		this.idNoteDefinition = idNoteDefinition;
	}

	public NoteDefinitionBO getNoteDefinition() {
		return noteDefinition;
	}

	public void setNoteDefinition(NoteDefinitionBO noteDefinition) {
		this.noteDefinition = noteDefinition;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "NoteValueBO [idStudent=" + idStudent + ", student=" + student
				+ ", idNoteDefinition=" + idNoteDefinition
				+ ", noteDefinition=" + noteDefinition + ", value=" + value
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}