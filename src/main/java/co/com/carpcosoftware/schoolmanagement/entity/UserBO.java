/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzschoolxuser;
import co.com.carpcosoftware.schoolmanagement.hibernate.Bzuser;
import co.com.carpcosoftware.schoolmanagement.hibernate.Bzuserxusertype;
import co.com.carpcosoftware.schoolmanagement.util.ImageUtil;

/**
 * User business object
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 24/03/2015
 */
@XmlRootElement(name="users")
public class UserBO extends AbstractBO implements Serializable, Comparable<UserBO> {

	/**
	 * Auto generated serial version
	 */
	private static final long serialVersionUID = 1337704244736040283L;

	/**
	 * Document number 
	 */
	private String documentNumber;

	/**
	 * Document type
	 */
	private String documentType;

	/**
	 * Last name
	 */
	private String lastName;

	/**
	 * Born date
	 */
	private Date born;

	/**
	 * Address
	 */
	private String address;

	/**
	 * First phone number
	 */
	private BigInteger phone1;

	/**
	 * Second phone number
	 */
	private BigInteger phone2;

	/**
	 * Password used by user to login in page
	 */
	private String password;

	/**
	 * Gender
	 */
	private String gender;

	/**
	 * Photo
	 */
	private Image photo;
	
	/**
	 * Photo turned into a byte array as string
	 */
	private String photoAsString;

	/**
	 * First user's guardian
	 */
	private UserBO guardian1;

	/**
	 * Second user's guardian
	 */
	private UserBO guardian2;

	/**
	 * {@link SchoolBO}
	 */
	private SchoolBO school;

	/**
	 * {@link UserTypeBO} set
	 */
	private Set<UserTypeBO> userTypeSet;
	
	/**
	 * Default constructor
	 */
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
	    this.phone2 = bzUser.getPhone2() != null ? BigInteger.valueOf(bzUser.getPhone2()) : null;
	    this.password = bzUser.getPassword();
	    this.gender = bzUser.getGender();
	    this.photo = ImageUtil.byteArrayToBufferedImage(bzUser.getPhoto());
	    this.guardian1 = bzUser.getBzuserByIdGuardian1() != null
	            ? new UserBO(bzUser.getBzuserByIdGuardian1()) : null;
	    this.guardian2 = bzUser.getBzuserByIdGuardian2() != null
	            ? new UserBO(bzUser.getBzuserByIdGuardian2()) : null;
	    this.school = new SchoolBO(
	            ((Bzschoolxuser)bzUser.getBzschoolxusers().iterator().next()).getBzschool());
	    this.creation = bzUser.getCreation();
	    this.updated = bzUser.getUpdated();
	    this.enabled = bzUser.isEnabled();
	    
	    @SuppressWarnings("unchecked")
		Set<Bzuserxusertype> bzUserXUserTypeSet = bzUser.getBzuserxusertypes();
	    if (bzUserXUserTypeSet != null) {
	    	this.userTypeSet = new HashSet<>();
	    	bzUserXUserTypeSet.stream().forEach((bzUserXUserType) -> {
	  	      userTypeSet.add(new UserTypeBO(bzUserXUserType.getCnusertype()));
	  	    });
	    }
	    
	    if (this.photo != null) {
			this.photoAsString = ImageUtil.encodeToByteArray((BufferedImage) this.photo).toString();
		}
	}

	/**
	 * @return the documentNumber
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber
	 *            the documentNumber to set
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType
	 *            the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the born
	 */
	public Date getBorn() {
		return born;
	}

	/**
	 * @param born
	 *            the born to set
	 */
	public void setBorn(Date born) {
		this.born = born;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone1
	 */
	public BigInteger getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1
	 *            the phone1 to set
	 */
	public void setPhone1(BigInteger phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public BigInteger getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2
	 *            the phone2 to set
	 */
	public void setPhone2(BigInteger phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	public Image getPhoto() {
		return photo;
	}

	public void setPhoto(Image photo) {
		this.photo = photo;
	}

	/**
	 * @return the photoAsString
	 */
	public String getPhotoAsString() {
		return photoAsString;
	}

	/**
	 * @param photoAsString the photoAsString to set
	 */
	public void setPhotoAsString(String photoAsString) {
		this.photoAsString = photoAsString;
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

	/**
	 * @return the userTypeSet
	 */
	public Set<UserTypeBO> getUserTypeSet() {
		return userTypeSet;
	}

	/**
	 * @param userTypeSet
	 *            the userTypeSet to set
	 */
	public void setUserTypeSet(Set<UserTypeBO> userTypeSet) {
		this.userTypeSet = userTypeSet;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 73 * hash + Objects.hashCode(this.id);
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
		return Objects.equals(this.documentType, other.documentType);
	}	
	
	/* (non-Javadoc)
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
