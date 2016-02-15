package co.com.soinsoftware.schoolmanagement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bznotevalue;

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

	public NoteValueBO(final Bznotevalue bzNoteValue) {
		super();
		if (bzNoteValue.getId() != null) {
			this.idStudent = bzNoteValue.getId().getIdUser();
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

	public void setIdStudent(final int idStudent) {
		this.idStudent = idStudent;
	}

	public UserBO getStudent() {
		return student;
	}

	public void setStudent(final UserBO student) {
		this.student = student;
	}

	public int getIdNoteDefinition() {
		return idNoteDefinition;
	}

	public void setIdNoteDefinition(final int idNoteDefinition) {
		this.idNoteDefinition = idNoteDefinition;
	}

	public NoteDefinitionBO getNoteDefinition() {
		return noteDefinition;
	}

	public void setNoteDefinition(final NoteDefinitionBO noteDefinition) {
		this.noteDefinition = noteDefinition;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(final BigDecimal value) {
		this.value = value;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(final Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
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