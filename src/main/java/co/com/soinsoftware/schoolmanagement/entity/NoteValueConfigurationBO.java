package co.com.soinsoftware.schoolmanagement.entity;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Cnnotevalue;

/**
 * Note Value Configuration object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 15/04/2016
 */
@XmlRootElement(name = "noteValueConf")
public class NoteValueConfigurationBO extends AbstractWithCodeBO implements
		Comparable<NoteValueConfigurationBO> {

	private static final long serialVersionUID = 5622827774843357933L;
	
	private BigDecimal rangeStart;
	
	private BigDecimal rangeEnd;

	public NoteValueConfigurationBO() {
		super();
	}

	public NoteValueConfigurationBO(final Cnnotevalue cnnotevalue) {
		super(cnnotevalue.getId(), cnnotevalue.getCode(), cnnotevalue.getName(), cnnotevalue
				.getCreation(), cnnotevalue.getUpdated(), cnnotevalue.isEnabled());
		this.rangeStart = cnnotevalue.getRangestart();
		this.rangeEnd = cnnotevalue.getRangeend();
	}

	public BigDecimal getRangeStart() {
		return rangeStart;
	}

	public void setRangeStart(BigDecimal rangeStart) {
		this.rangeStart = rangeStart;
	}

	public BigDecimal getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(BigDecimal rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

	@Override
	public String toString() {
		return "NoteValueConfigurationBO [rangeStart=" + rangeStart
				+ ", rangeEnd=" + rangeEnd + ", code=" + code + ", id=" + id
				+ ", name=" + name + ", creation=" + creation + ", updated="
				+ updated + ", enabled=" + enabled + "]";
	}

	@Override
	public int compareTo(final NoteValueConfigurationBO other) {
		return this.name.compareTo(other.name);
	}
}