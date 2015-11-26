/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.entity;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzyear;

/**
 * Year business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
@XmlRootElement(name = "years")
public class YearBO extends AbstractBO implements Comparable<YearBO> {

	/**
	 * Auto generated serial version
	 */
	private static final long serialVersionUID = -6670213337879299160L;

	public YearBO() {
		super();
	}

	public YearBO(Bzyear bzYear) {
		super();
		this.id = bzYear.getId();
		this.name = bzYear.getName();
		this.creation = bzYear.getCreation();
		this.updated = bzYear.getUpdated();
		this.enabled = bzYear.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YearBO [id=" + id + ", name=" + name + ", creation=" + creation
				+ ", updated=" + updated + ", enabled=" + enabled + "]";
	}

	@Override
	public int compareTo(YearBO other) {
		Integer thisName = Integer.parseInt(this.name);
		Integer otherName = Integer.parseInt(other.getName());
		return thisName.compareTo(otherName) * -1;
	}
}