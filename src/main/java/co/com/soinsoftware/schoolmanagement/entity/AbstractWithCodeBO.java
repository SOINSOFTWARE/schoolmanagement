/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

/**
 * Abstract object that provides code column necessary for multiple BO objects
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 26/11/2015
 */
public class AbstractWithCodeBO extends AbstractBO {

	private static final long serialVersionUID = -2024771930579614306L;
	
	protected String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "AbstractWithCodeBO [code=" + code + ", id=" + id + ", name="
				+ name + ", creation=" + creation + ", updated=" + updated
				+ ", enabled=" + enabled + "]";
	}
}