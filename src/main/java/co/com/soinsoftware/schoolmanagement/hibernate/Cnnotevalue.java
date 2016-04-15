package co.com.soinsoftware.schoolmanagement.hibernate;

// Generated 15-Apr-2016 16:37:40 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

/**
 * Cnnote generated by hbm2java
 */
@SuppressWarnings({ "serial" })
public class Cnnotevalue implements java.io.Serializable {

	private Integer id;
	private Cnnote cnnote;
	private String code;
	private String name;
	private BigDecimal rangestart;
	private BigDecimal rangeend;
	private boolean approved;
	private Date creation;
	private Date updated;
	private boolean enabled;

	public Cnnotevalue() {
	}

	public Cnnotevalue(Cnnote cnnote, String code, String name,
			BigDecimal rangestart, BigDecimal rangeend, Date creation,
			Date updated, boolean enabled) {
		this.cnnote = cnnote;
		this.code = code;
		this.name = name;
		this.rangestart = rangestart;
		this.rangeend = rangeend;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cnnote getCnnote() {
		return cnnote;
	}

	public void setCnnote(Cnnote cnnote) {
		this.cnnote = cnnote;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRangestart() {
		return rangestart;
	}

	public void setRangestart(BigDecimal rangestart) {
		this.rangestart = rangestart;
	}

	public BigDecimal getRangeend() {
		return rangeend;
	}

	public void setRangeend(BigDecimal rangeend) {
		this.rangeend = rangeend;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Date getCreation() {
		return this.creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
