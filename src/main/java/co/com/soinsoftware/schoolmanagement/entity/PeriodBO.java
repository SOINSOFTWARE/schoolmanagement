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

	private static final long serialVersionUID = -6503035896094998534L;

	private YearBO year;

	public PeriodBO() {
		super();
	}

	public PeriodBO(final Bzperiod bzPeriod, final YearBO year) {
		super(bzPeriod.getId(), bzPeriod.getName(), bzPeriod.getCreation(),
				bzPeriod.getUpdated(), bzPeriod.isEnabled());
		this.year = year;
	}

	public YearBO getYear() {
		return year;
	}

	public void setYear(final YearBO year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "PeriodBO [year=" + year + ", id=" + id + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}