package co.com.soinsoftware.schoolmanagement.entity;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bztime;

/**
 * Time business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 21/08/2015
 */
@XmlRootElement(name = "times")
public class TimeBO extends AbstractWithCodeBO implements Comparable<TimeBO> {

	private static final long serialVersionUID = 5622827774843357933L;

	public TimeBO() {
		super();
	}

	public TimeBO(final Bztime bzTime) {
		super(bzTime.getId(), bzTime.getCode(), bzTime.getName(), bzTime
				.getCreation(), bzTime.getUpdated(), bzTime.isEnabled());
	}

	@Override
	public String toString() {
		return "TimeBO [id=" + id + ", code=" + code + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public int compareTo(final TimeBO other) {
		return this.name.compareTo(other.name);
	}
}