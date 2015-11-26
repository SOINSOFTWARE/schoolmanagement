/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bznotedefinition;

/**
 * Note definition business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 26/11/2015
 */
@XmlRootElement(name = "notedefinitions")
public class NoteDefinitionBO extends AbstractBO {

	private static final long serialVersionUID = -7810207741825223847L;

	private String description;

	private BigDecimal value;

	private PeriodBO period;

	private Set<NoteValueBO> noteValueSet;

	public NoteDefinitionBO() {
		super();
	}

	public NoteDefinitionBO(Bznotedefinition bzNoteDefinition) {
		super();
		this.id = bzNoteDefinition.getId();
		this.name = bzNoteDefinition.getName();
		this.description = bzNoteDefinition.getDescription();
		this.value = bzNoteDefinition.getValue();
		this.period = (bzNoteDefinition.getBzperiod() != null) ? new PeriodBO(
				bzNoteDefinition.getBzperiod()) : null;
		this.creation = bzNoteDefinition.getCreation();
		this.updated = bzNoteDefinition.getUpdated();
		this.enabled = bzNoteDefinition.isEnabled();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public PeriodBO getPeriod() {
		return period;
	}

	public void setPeriod(PeriodBO period) {
		this.period = period;
	}

	public Set<NoteValueBO> getNoteValueSet() {
		return noteValueSet;
	}

	public void setNoteValueSet(Set<NoteValueBO> noteValueSet) {
		this.noteValueSet = noteValueSet;
	}

	@Override
	public String toString() {
		return "NoteDefinitionBO [description=" + description + ", value="
				+ value + ", period=" + period + ", noteValueSet="
				+ noteValueSet + ", id=" + id + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}