package co.com.carpcosoftware.schoolmanagement.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bztime;

/**
 * Time business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 21/08/2015
 */
@XmlRootElement(name = "times")
public class TimeBO extends AbstractBO implements Serializable,
		Comparable<TimeBO> {

	/**
	 * Auto generated serial version
	 */
	private static final long serialVersionUID = 5622827774843357933L;

	public TimeBO() {
		super();
	}

	public TimeBO(Bztime bzTime) {
		super();
		this.id = bzTime.getId();
		this.code = bzTime.getCode();
		this.name = bzTime.getName();
		this.creation = bzTime.getCreation();
		this.updated = bzTime.getUpdated();
		this.enabled = bzTime.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimeBO [id=" + id + ", code=" + code + ", name=" + name
				+ ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public int compareTo(TimeBO other) {
		return this.name.compareTo(other.name);
	}

}
