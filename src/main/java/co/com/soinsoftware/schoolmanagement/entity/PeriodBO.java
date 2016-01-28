/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzperiod;

/**
 * Period business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 26/11/2015
 */
@XmlRootElement(name = "periods")
public class PeriodBO extends AbstractBO {

	private static final long serialVersionUID = 1L;

	private YearBO year;

	public PeriodBO() {
		super();
	}

	public PeriodBO(Bzperiod bzPeriod) {
		super();
		this.id = bzPeriod.getId();
		this.name = bzPeriod.getName();
		this.year = (bzPeriod.getBzyear() != null) ? new YearBO(
				bzPeriod.getBzyear()) : null;
		this.creation = bzPeriod.getCreation();
		this.updated = bzPeriod.getUpdated();
		this.enabled = bzPeriod.isEnabled();
	}

	public YearBO getYear() {
		return year;
	}

	public void setYear(YearBO year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "PeriodBO [year=" + year + ", id=" + id + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}