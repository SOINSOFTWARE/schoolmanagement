/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzclass;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzschoolxuser;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzuser;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzuserxusertype;

/**
 * User business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 24/03/2015
 */
@XmlRootElement(name = "users")
public class UserBO extends AbstractBO implements Comparable<UserBO> {

	private static final long serialVersionUID = 1337704244736040283L;

	private String documentNumber;

	private String documentType;

	private String lastName;

	private Date born;

	private String address;

	private BigInteger phone1;

	private BigInteger phone2;

	private String password;

	private String gender;

	private String photo;

	private UserBO guardian1;

	private UserBO guardian2;

	private SchoolBO school;

	private Set<UserTypeBO> userTypeSet;

	private Set<ClassBO> classSet;

	public UserBO() {
		super();
	}

	public UserBO(Bzuser bzUser) {
		super();
		this.id = bzUser.getId();
		this.documentNumber = bzUser.getDocumentNumber();
		this.documentType = bzUser.getDocumentType();
		this.name = bzUser.getName();
		this.lastName = bzUser.getLastName();
		this.born = bzUser.getBorn();
		this.address = bzUser.getAddress();
		this.phone1 = BigInteger.valueOf(bzUser.getPhone1());
		this.phone2 = bzUser.getPhone2() != null ? BigInteger.valueOf(bzUser
				.getPhone2()) : null;
		this.password = bzUser.getPassword();
		this.gender = bzUser.getGender();
		this.photo = bzUser.getPhoto();
		this.guardian1 = bzUser.getBzuserByIdGuardian1() != null ? new UserBO(
				bzUser.getBzuserByIdGuardian1()) : null;
		this.guardian2 = bzUser.getBzuserByIdGuardian2() != null ? new UserBO(
				bzUser.getBzuserByIdGuardian2()) : null;
		this.school = new SchoolBO(((Bzschoolxuser) bzUser.getBzschoolxusers()
				.iterator().next()).getBzschool());
		this.creation = bzUser.getCreation();
		this.updated = bzUser.getUpdated();
		this.enabled = bzUser.isEnabled();

		Set<?> bzUserXUserTypeSet = bzUser.getBzuserxusertypes();
		if (bzUserXUserTypeSet != null && !bzUserXUserTypeSet.isEmpty()) {
			this.userTypeSet = new HashSet<>();
			bzUserXUserTypeSet.stream().forEach(
					(bzUserXUserType) -> {
						userTypeSet.add(new UserTypeBO(
								((Bzuserxusertype) bzUserXUserType)
										.getCnusertype()));
					});
		}

		Set<?> bzClassSet = bzUser.getBzclasses();
		if (bzClassSet != null && !bzClassSet.isEmpty()) {
			this.classSet = new HashSet<>();
			bzClassSet.stream().forEach((bzClass) -> {
				classSet.add(new ClassBO(((Bzclass) bzClass)));
			});
		}
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigInteger getPhone1() {
		return phone1;
	}

	public void setPhone1(BigInteger phone1) {
		this.phone1 = phone1;
	}

	public BigInteger getPhone2() {
		return phone2;
	}

	public void setPhone2(BigInteger phone2) {
		this.phone2 = phone2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public UserBO getGuardian1() {
		return guardian1;
	}

	public void setGuardian1(UserBO guardian1) {
		this.guardian1 = guardian1;
	}

	public UserBO getGuardian2() {
		return guardian2;
	}

	public void setGuardian2(UserBO guardian2) {
		this.guardian2 = guardian2;
	}

	public SchoolBO getSchool() {
		return school;
	}

	public void setSchool(SchoolBO school) {
		this.school = school;
	}

	public Set<UserTypeBO> getUserTypeSet() {
		return userTypeSet;
	}

	public void setUserTypeSet(Set<UserTypeBO> userTypeSet) {
		this.userTypeSet = userTypeSet;
	}

	public Set<ClassBO> getClassSet() {
		return classSet;
	}

	public void setClassSet(Set<ClassBO> classSet) {
		this.classSet = classSet;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 73 * hash + Objects.hashCode(this.documentNumber);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UserBO other = (UserBO) obj;
		if (!Objects.equals(this.documentNumber, other.documentNumber)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserBO [id=" + id + ", documentNumber=" + documentNumber
				+ ", documentType=" + documentType + ", name=" + name
				+ ", lastName=" + lastName + ", born=" + born + ", address="
				+ address + ", phone1=" + phone1 + ", phone2=" + phone2
				+ ", password=" + password + ", gender=" + gender + ", photo="
				+ photo + ", guardian1=" + guardian1 + ", guardian2="
				+ guardian2 + ", school=" + school + ", creation=" + creation
				+ ", updated=" + updated + ", enabled=" + enabled
				+ ", userTypeSet=" + userTypeSet + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(UserBO userBO) {
		return this.lastName.compareToIgnoreCase(userBO.getLastName());
	}

}