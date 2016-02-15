package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.UserDAO;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;
import co.com.soinsoftware.schoolmanagement.entity.UserTypeBO;
import co.com.soinsoftware.schoolmanagement.entity.YearBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroomxuser;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzuser;

/**
 * User business logic layer
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 16/04/2015
 */
@Service
public class UserBLL extends AbstractBLL implements
		IBusinessLogicLayer<UserBO, Bzuser> {

	@Autowired
	private ClassBLL classBLL;

	@Autowired
	private ClassRoomBLL classRoomBLL;

	@Autowired
	private SchoolBLL schoolBLL;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserTypeBLL userTypeBLL;

	@Autowired
	private YearBLL yearBLL;

	@Override
	public Set<UserBO> findAll() {
		return this.isCacheEmpty(USER_KEY) ? this.selectAndPutInCache() : this
				.getObjectsFromCache();
	}

	@Override
	public Set<UserBO> findAll(final int idSchool) {
		return this.isCacheEmpty(USER_KEY) ? this.selectAndPutInCache() : this
				.getObjectsFromCache();
	}

	@Override
	public UserBO findByIdentifier(final Integer identifier) {
		UserBO userBO = null;
		if (this.isCacheEmpty(USER_KEY)) {
			userBO = this.selectByIdentifierAndPutInCache(identifier);
		} else {
			userBO = (UserBO) this.getObjectFromCache(USER_KEY, identifier);
		}
		LOGGER.info("User = {} was loaded successfully", userBO.toString());
		return userBO;
	}

	@Override
	public UserBO findByCode(final int idSchool, final String code,
			final int identifier) {
		UserBO userBO = this.getUserBOFromCache(code);
		if (userBO == null) {
			userBO = this.selectByDocumentNumer(code);
		}
		return userBO;
	}

	@Override
	public UserBO saveRecord(final UserBO record) {
		return null;
	}

	@Override
	public Set<UserBO> selectAndPutInCache() {
		final Set<Bzuser> bzUserSet = this.userDAO.select();
		final Set<UserBO> userBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzUserSet);
		if (userBOSet != null) {
			this.putObjectsInCache(USER_KEY, userBOSet);
		}
		return userBOSet;
	}

	@Override
	public Set<UserBO> createEntityBOSetUsingHibernatEntities(
			final Set<?> hibernateEntitySet) {
		Set<UserBO> userBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			userBOSet = new HashSet<>();
			for (Object bzUser : hibernateEntitySet) {
				if (bzUser instanceof Bzuser) {
					userBOSet.add(this.buildUserBO((Bzuser) bzUser, true));
				}
			}
		}
		return userBOSet;
	}

	@Override
	public UserBO selectByIdentifierAndPutInCache(final Integer identifier) {
		UserBO userBO = null;
		final Bzuser bzUser = this.userDAO.selectByIdentifier(identifier);
		if (bzUser != null) {
			userBO = this.buildUserBO(bzUser, true);
			this.putObjectInCache(USER_KEY, userBO);
		}
		return userBO;
	}

	public UserBO selectByDocumentNumer(final String documentNumber) {
		UserBO userBO = null;
		final Bzuser bzUser = this.userDAO
				.selectByDocumentNumber(documentNumber);
		if (bzUser != null) {
			userBO = this.buildUserBO(bzUser, true);
			this.putObjectInCache(USER_KEY, userBO);
		}
		return userBO;
	}

	public UserBO getUserBOFromCache(final String documentNumber) {
		UserBO userBO = null;
		final Cache cache = this.getCacheUsingKey(USER_KEY);
		for (Object key : cache.getKeys()) {
			final Object objectFromCache = this.getObjectFromCache(USER_KEY,
					(Integer) key);
			if (objectFromCache != null
					&& ((UserBO) objectFromCache).getDocumentNumber().equals(
							documentNumber)) {
				userBO = (UserBO) objectFromCache;
				break;
			}
		}
		return userBO;
	}

	public Set<UserBO> findByType(final int schoolId, final String userTypeCode) {
		final Set<UserBO> userBOSet = new HashSet<>();
		if (schoolId > 0 && userTypeBLL.isValidUserType(userTypeCode)) {
			final Set<UserBO> cacheUserBOSet = this.getObjectsFromCache();
			final UserTypeBO userTypeBO = userTypeBLL.findByCode(schoolId,
					userTypeCode, 0);
			if (cacheUserBOSet != null && userTypeBO != null) {
				for (UserBO userBO : cacheUserBOSet) {
					if (this.isLinkedToSchool(userBO, schoolId)
							&& this.isLinkedToUserType(userBO, userTypeBO)) {
						userBOSet.add(userBO);
					}
				}
			}
		}
		return userBOSet;
	}

	public Set<UserBO> findTeacherNoGroupDirectors(final int schoolId) {
		final Set<UserBO> userBOSet = new HashSet<>();
		final Set<UserBO> cacheUserBOSet = this.getObjectsFromCache();
		final YearBO yearBO = yearBLL.findCurrentYear();
		final Set<ClassRoomBO> classRoomBOSet = classRoomBLL.findBy(null,
				schoolId, yearBO.getName(), null, null);
		final UserTypeBO userTypeBO = userTypeBLL.findByCode(schoolId,
				UserTypeBO.TEACHER, 0);
		if (cacheUserBOSet != null) {
			for (UserBO userBO : cacheUserBOSet) {
				if (this.isLinkedToSchool(userBO, schoolId)
						&& this.isLinkedToUserType(userBO, userTypeBO)
						&& !this.isTeacherADirector(classRoomBOSet, userBO)) {
					userBOSet.add(userBO);
					LOGGER.info(
							"Teacher that is not a group director yet : {}",
							userBO.toString());
				}
			}
		}
		return userBOSet;
	}

	public boolean isLinkedToSchool(final UserBO userBO, final int schoolId) {
		boolean linked = false;
		for (final SchoolBO school : userBO.getSchoolSet()) {
			if (school.getId().equals(schoolId)) {
				linked = true;
				break;
			}
		}
		return linked;
	}

	private boolean isLinkedToUserType(final UserBO userBO,
			final UserTypeBO userTypeBO) {
		return userBO.getUserTypeSet().contains(userTypeBO);
	}

	private boolean isTeacherADirector(final Set<ClassRoomBO> classRoomBOSet,
			UserBO userBO) {
		boolean isDirector = false;
		if (classRoomBOSet != null) {
			for (final ClassRoomBO classRoomBO : classRoomBOSet) {
				if (classRoomBO.getTeacher().equals(userBO)) {
					isDirector = true;
					break;
				}
			}
		}
		return isDirector;
	}

	@SuppressWarnings("unchecked")
	protected Set<UserBO> getObjectsFromCache() {
		return this.getObjectsFromCache(USER_KEY);
	}

	public Bzuser buildHibernateEntity(final UserBO userBO) {
		Bzuser bzUser = new Bzuser();
		if (userBO != null) {
			bzUser.setId(userBO.getId());
			bzUser.setDocumentNumber(userBO.getDocumentNumber());
			bzUser.setDocumentType(userBO.getDocumentType());
			bzUser.setName(userBO.getName());
			bzUser.setLastName(userBO.getLastName());
			bzUser.setBorn(userBO.getBorn());
			bzUser.setAddress(userBO.getAddress());
			bzUser.setPhone1(userBO.getPhone1().longValue());
			bzUser.setPhone2(userBO.getPhone2() != null ? userBO.getPhone2()
					.longValue() : null);
			bzUser.setPassword(userBO.getPassword());
			bzUser.setGender(userBO.getGender());
			bzUser.setPhoto(userBO.getPhoto());
			bzUser.setBzuserByIdGuardian1(this.buildHibernateEntity(userBO
					.getGuardian1()));
			bzUser.setBzuserByIdGuardian2(this.buildHibernateEntity(userBO
					.getGuardian2()));
			bzUser.setCreation(userBO.getCreation());
			bzUser.setUpdated(userBO.getUpdated());
			bzUser.setEnabled(userBO.isEnabled());
		}
		return bzUser;
	}

	@Override
	public UserBO putObjectInCache(Bzuser record) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public UserBO buildUserBO(final Bzuser bzUser, final boolean includeClassBO) {
		UserBO user = null;
		if (bzUser != null) {
			final Set<SchoolBO> schoolSet = schoolBLL.buildSchoolSet(bzUser
					.getBzschoolxusers());
			final UserBO guardian1 = this.buildUserBO(
					bzUser.getBzuserByIdGuardian1(), includeClassBO);
			final UserBO guardian2 = this.buildUserBO(
					bzUser.getBzuserByIdGuardian2(), includeClassBO);
			final Set<UserTypeBO> userTypeSet = userTypeBLL
					.buildUserTypeSet(bzUser.getBzuserxusertypes());
			Set<ClassBO> classSet = null;
			if (includeClassBO) {
				classSet = classBLL.buildClassSet(bzUser.getBzclasses());
			}
			user = new UserBO(bzUser, schoolSet, guardian1, guardian2,
					userTypeSet, classSet);
		}
		return user;
	}

	public Set<UserBO> buildUserSet(
			final Set<Bzclassroomxuser> bzClassRoomXUserSet) {
		final Set<UserBO> userSet = new HashSet<>();
		for (final Bzclassroomxuser bzClassRoomXuser : bzClassRoomXUserSet) {
			final UserBO user = this.buildUserBO(bzClassRoomXuser.getBzuser(), false);
			userSet.add(user);
		}
		return userSet;
	}
}