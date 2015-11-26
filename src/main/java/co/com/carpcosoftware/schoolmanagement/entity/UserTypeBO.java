/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.carpcosoftware.schoolmanagement.hibernate.Cnusertype;
import co.com.carpcosoftware.schoolmanagement.hibernate.Cnusertypexaccess;

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

	/**
	 * {@link AccessBO} set granted to {@link UserTypeBO}
	 */
	private Set<AccessBO> accessSet;

	public UserTypeBO() {
		super();
	}

	public UserTypeBO(Cnusertype cnusertype) {
		super();
		this.id = cnusertype.getId();
		this.code = cnusertype.getCode();
		this.name = cnusertype.getName();
		this.creation = cnusertype.getCreation();
		this.updated = cnusertype.getUpdated();
		this.enabled = cnusertype.isEnabled();

		@SuppressWarnings("unchecked")
		Set<Cnusertypexaccess> cnUserTypeXAccessSet = cnusertype
				.getCnuserttypexaccesses();
		if (cnUserTypeXAccessSet != null) {
			this.accessSet = new HashSet<>();
			cnUserTypeXAccessSet.stream().forEach((cnUserTypeXAccess) -> {
				accessSet.add(new AccessBO(cnUserTypeXAccess.getCnaccess()));
			});
		}
	}

	public Set<AccessBO> getAccessSet() {
		return accessSet;
	}

	public void setAccessSet(Set<AccessBO> accessSet) {
		this.accessSet = accessSet;
	}

	public void addAccessToSet(AccessBO accessBO) {
		if (this.accessSet == null) {
			this.accessSet = new HashSet<AccessBO>();
		}
		this.accessSet.add(accessBO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserTypeBO [accessSet=" + accessSet + ", id=" + id + ", code="
				+ code + ", name=" + name + ", creation=" + creation
				+ ", updated=" + updated + ", enabled=" + enabled + "]";
	}
}