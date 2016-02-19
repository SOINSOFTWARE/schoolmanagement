/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzuser;

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

	private Set<SchoolBO> schoolSet;

	private Set<UserTypeBO> userTypeSet;

	private Set<ClassBO> classSet;
	
	private ClassRoomBO lastClassRoom;

	public UserBO() {
		super();
	}

	public UserBO(final Bzuser bzUser, final Set<SchoolBO> schoolSet,
			final UserBO guardian1, final UserBO guardian2,
			final Set<UserTypeBO> userTypeSet) {
		super(bzUser.getId(), bzUser.getName(), bzUser.getCreation(), bzUser
				.getUpdated(), bzUser.isEnabled());
		this.documentNumber = bzUser.getDocumentNumber();
		this.documentType = bzUser.getDocumentType();
		this.lastName = bzUser.getLastName();
		this.born = bzUser.getBorn();
		this.address = bzUser.getAddress();
		this.phone1 = BigInteger.valueOf(bzUser.getPhone1());
		this.phone2 = bzUser.getPhone2() != null ? BigInteger.valueOf(bzUser
				.getPhone2()) : null;
		this.password = bzUser.getPassword();
		this.gender = bzUser.getGender();
		this.photo = bzUser.getPhoto();
		this.guardian1 = guardian1;
		this.guardian2 = guardian2;
		this.schoolSet = schoolSet;
		this.userTypeSet = userTypeSet;
	}

	public UserBO(final Bzuser bzUser, final Set<SchoolBO> schoolSet,
			final UserBO guardian1, final UserBO guardian2,
			final Set<UserTypeBO> userTypeSet, final Set<ClassBO> classSet) {
		this(bzUser, schoolSet, guardian1, guardian2, userTypeSet);
		this.classSet = classSet;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(final String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(final String documentType) {
		this.documentType = documentType;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getBorn() {
		return born;
	}

	public void setBorn(final Date born) {
		this.born = born;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public BigInteger getPhone1() {
		return phone1;
	}

	public void setPhone1(final BigInteger phone1) {
		this.phone1 = phone1;
	}

	public BigInteger getPhone2() {
		return phone2;
	}

	public void setPhone2(final BigInteger phone2) {
		this.phone2 = phone2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public UserBO getGuardian1() {
		return guardian1;
	}

	public void setGuardian1(final UserBO guardian1) {
		this.guardian1 = guardian1;
	}

	public UserBO getGuardian2() {
		return guardian2;
	}

	public void setGuardian2(final UserBO guardian2) {
		this.guardian2 = guardian2;
	}

	public Set<SchoolBO> getSchoolSet() {
		return schoolSet;
	}

	public void setSchoolSet(final Set<SchoolBO> schoolSet) {
		this.schoolSet = schoolSet;
	}

	public Set<UserTypeBO> getUserTypeSet() {
		return userTypeSet;
	}

	public void setUserTypeSet(final Set<UserTypeBO> userTypeSet) {
		this.userTypeSet = userTypeSet;
	}

	public Set<ClassBO> getClassSet() {
		return classSet;
	}

	public void setClassSet(final Set<ClassBO> classSet) {
		this.classSet = classSet;
	}

	public ClassRoomBO getLastClassRoom() {
		return lastClassRoom;
	}

	public void setLastClassRoom(ClassRoomBO lastClassRoom) {
		this.lastClassRoom = lastClassRoom;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 73 * hash + Objects.hashCode(this.documentNumber);
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
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

	@Override
	public String toString() {
		return "UserBO [id=" + id + ", documentNumber=" + documentNumber
				+ ", documentType=" + documentType + ", name=" + name
				+ ", lastName=" + lastName + ", born=" + born + ", address="
				+ address + ", phone1=" + phone1 + ", phone2=" + phone2
				+ ", password=" + password + ", gender=" + gender + ", photo="
				+ photo + ", guardian1=" + guardian1 + ", guardian2="
				+ guardian2 + ", schoolSet=" + schoolSet + ", creation="
				+ creation + ", updated=" + updated + ", enabled=" + enabled
				+ ", userTypeSet=" + userTypeSet + "]";
	}

	@Override
	public int compareTo(final UserBO userBO) {
		return this.lastName.compareToIgnoreCase(userBO.getLastName())
				* this.name.compareToIgnoreCase(userBO.getName());
	}
}