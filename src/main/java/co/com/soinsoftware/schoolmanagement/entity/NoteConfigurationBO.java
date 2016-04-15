package co.com.soinsoftware.schoolmanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Cnnote;
import co.com.soinsoftware.schoolmanagement.hibernate.Cnnotevalue;

/**
 * Note Configuration object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 15/04/2016
 */
@XmlRootElement(name = "noteConf")
public class NoteConfigurationBO extends AbstractWithCodeBO implements
		Comparable<NoteConfigurationBO> {

	private static final long serialVersionUID = 5622827774843357933L;

	private Set<NoteValueConfigurationBO> noteValueSet;

	public NoteConfigurationBO() {
		super();
	}

	@SuppressWarnings("unchecked")
	public NoteConfigurationBO(final Cnnote cnnote) {
		super(cnnote.getId(), cnnote.getCode(), cnnote.getName(), cnnote
				.getCreation(), cnnote.getUpdated(), cnnote.isEnabled());
		if (cnnote.getCnnotevalues() != null
				&& !cnnote.getCnnotevalues().isEmpty()) {
			noteValueSet = new HashSet<>();
			Set<Cnnotevalue> cnNoteValueSet = new HashSet<>(
					cnnote.getCnnotevalues());
			for (final Cnnotevalue cnnotevalue : cnNoteValueSet) {
				final NoteValueConfigurationBO noteValue = new NoteValueConfigurationBO(
						cnnotevalue);
				noteValueSet.add(noteValue);
			}
		}
	}

	public Set<NoteValueConfigurationBO> getNoteValueSet() {
		return noteValueSet;
	}

	public void setNoteValueSet(Set<NoteValueConfigurationBO> noteValueSet) {
		this.noteValueSet = noteValueSet;
	}

	@Override
	public String toString() {
		return "NoteConfigurationBO [noteValueSet=" + noteValueSet + ", code="
				+ code + ", id=" + id + ", name=" + name + ", creation="
				+ creation + ", updated=" + updated + ", enabled=" + enabled
				+ "]";
	}

	@Override
	public int compareTo(final NoteConfigurationBO other) {
		return this.name.compareTo(other.name);
	}
}