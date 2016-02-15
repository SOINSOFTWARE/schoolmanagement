/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzyear;

/**
 * Year business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
@XmlRootElement(name = "years")
public class YearBO extends AbstractBO implements Comparable<YearBO> {

	private static final long serialVersionUID = -6670213337879299160L;

	public YearBO() {
		super();
	}

	public YearBO(final Bzyear bzYear) {
		super(bzYear.getId(), bzYear.getName(), bzYear.getCreation(), bzYear
				.getUpdated(), bzYear.isEnabled());
	}

	@Override
	public String toString() {
		return "YearBO [id=" + id + ", name=" + name + ", creation=" + creation
				+ ", updated=" + updated + ", enabled=" + enabled + "]";
	}

	@Override
	public int compareTo(final YearBO other) {
		final Integer thisName = Integer.parseInt(this.name);
		final Integer otherName = Integer.parseInt(other.getName());
		return thisName.compareTo(otherName) * -1;
	}
}