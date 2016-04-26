/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bznotedefinition;

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

	private Integer value;

	private PeriodBO period;

	private Set<NoteValueBO> noteValueSet;
	
	private ClassBO classBO;
	
	private int idClass;
	
	private int idPeriod;

	public NoteDefinitionBO() {
		super();
	}

	public NoteDefinitionBO(final Bznotedefinition bzNoteDefinition,
			final PeriodBO period) {
		super(bzNoteDefinition.getId(), bzNoteDefinition.getName(),
				bzNoteDefinition.getCreation(), bzNoteDefinition.getUpdated(),
				bzNoteDefinition.isEnabled());
		this.description = bzNoteDefinition.getDescription();
		this.value = bzNoteDefinition.getValue();
		this.period = period;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(final Integer value) {
		this.value = value;
	}

	public PeriodBO getPeriod() {
		return period;
	}

	public void setPeriod(final PeriodBO period) {
		this.period = period;
	}

	public Set<NoteValueBO> getNoteValueSet() {
		return noteValueSet;
	}

	public void setNoteValueSet(final Set<NoteValueBO> noteValueSet) {
		this.noteValueSet = noteValueSet;
	}

	public ClassBO getClassBO() {
		return classBO;
	}

	public void setClassBO(ClassBO classBO) {
		this.classBO = classBO;
	}

	public int getIdClass() {
		return idClass;
	}

	public void setIdClass(int idClass) {
		this.idClass = idClass;
	}

	public int getIdPeriod() {
		return idPeriod;
	}

	public void setIdPeriod(int idPeriod) {
		this.idPeriod = idPeriod;
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