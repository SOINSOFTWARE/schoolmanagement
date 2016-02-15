/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Cnusertype;

/**
 * User type business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 24/03/2015
 */
@XmlRootElement(name = "userTypes")
public class UserTypeBO extends AbstractWithCodeBO {

	private static final long serialVersionUID = -2994642594740216071L;

	public static final String COORDINATOR = "COORD";

	public static final String DIRECTOR = "RCTOR";

	public static final String GUARDIAN = "GUARD";

	public static final String STUDENT = "ESTUD";

	public static final String TEACHER = "PROFE";

	private Set<AccessBO> accessSet;

	public UserTypeBO() {
		super();
	}

	public UserTypeBO(final Cnusertype cnusertype, final Set<AccessBO> accessSet) {
		super(cnusertype.getId(), cnusertype.getCode(), cnusertype.getName(),
				cnusertype.getCreation(), cnusertype.getUpdated(), cnusertype
						.isEnabled());
		this.accessSet = accessSet;
	}

	public Set<AccessBO> getAccessSet() {
		return accessSet;
	}

	public void setAccessSet(final Set<AccessBO> accessSet) {
		this.accessSet = accessSet;
	}

	public void addAccessToSet(final AccessBO accessBO) {
		if (this.accessSet == null) {
			this.accessSet = new HashSet<AccessBO>();
		}
		this.accessSet.add(accessBO);
	}

	@Override
	public String toString() {
		return "UserTypeBO [accessSet=" + accessSet + ", id=" + id + ", code="
				+ code + ", name=" + name + ", creation=" + creation
				+ ", updated=" + updated + ", enabled=" + enabled + "]";
	}
}