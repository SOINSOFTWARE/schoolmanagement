package co.com.soinsoftware.schoolmanagement.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzfinalnote;

/**
 * Final Note business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 13/05/2016
 */
@XmlRootElement(name = "finalnotes")
public class FinalNoteBO implements Serializable {

	private static final long serialVersionUID = -1294581087156310419L;

	private int idStudent;

	private int idClass;

	private int idPeriod;

	private BigDecimal value;

	private Date creation;

	private Date updated;

	private boolean enabled;

	public FinalNoteBO() {
		super();
	}

	public FinalNoteBO(final int idStudent, final int idClass,
			final int idPeriod, final BigDecimal value, final Date creation,
			final Date updated, final boolean enabled) {
		super();
		this.idStudent = idStudent;
		this.idClass = idClass;
		this.idPeriod = idPeriod;
		this.value = value;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public FinalNoteBO(final Bzfinalnote bzFinalNote) {
		super();
		if (bzFinalNote.getId() != null) {
			this.idStudent = bzFinalNote.getId().getIdUser();
			this.idClass = bzFinalNote.getId().getIdClass();
			this.idPeriod = bzFinalNote.getId().getIdPeriod();
		}
		this.value = bzFinalNote.getValue();
		this.creation = bzFinalNote.getCreation();
		this.updated = bzFinalNote.getUpdated();
		this.enabled = bzFinalNote.isEnabled();
	}

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(final int idStudent) {
		this.idStudent = idStudent;
	}

	public int getIdClass() {
		return idClass;
	}

	public void setIdClass(final int idClass) {
		this.idClass = idClass;
	}

	public int getIdPeriod() {
		return idPeriod;
	}

	public void setIdPeriod(final int idPeriod) {
		this.idPeriod = idPeriod;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idClass;
		result = prime * result + idPeriod;
		result = prime * result + idStudent;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinalNoteBO other = (FinalNoteBO) obj;
		if (idClass != other.idClass)
			return false;
		if (idPeriod != other.idPeriod)
			return false;
		if (idStudent != other.idStudent)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FinalNoteBO [idStudent=" + idStudent + ", idClass=" + idClass
				+ ", idPeriod=" + idPeriod + ", value=" + value + ", creation="
				+ creation + ", updated=" + updated + ", enabled=" + enabled
				+ "]";
	}
}