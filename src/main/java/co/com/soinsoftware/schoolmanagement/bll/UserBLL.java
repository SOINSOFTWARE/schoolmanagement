package co.com.soinsoftware.schoolmanagement.bll;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.SchoolXUserDAO;
import co.com.soinsoftware.schoolmanagement.dao.UserDAO;
import co.com.soinsoftware.schoolmanagement.dao.UserXUserTypeDAO;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;
import co.com.soinsoftware.schoolmanagement.entity.UserTypeBO;
import co.com.soinsoftware.schoolmanagement.entity.YearBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroomxuser;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzschool;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzschoolxuser;
import co.com.soinsoftware.schoolmanagement.hibernate.BzschoolxuserId;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzuser;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzuserxusertype;
import co.com.soinsoftware.schoolmanagement.hibernate.BzuserxusertypeId;
import co.com.soinsoftware.schoolmanagement.hibernate.Cnusertype;

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
	private SchoolXUserDAO schoolxUserDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserTypeBLL userTypeBLL;

	@Autowired
	private UserXUserTypeDAO userxUserTypeDAO;

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
		if (userBO != null) {
			LOGGER.info("User = {} was loaded successfully", userBO.toString());
		}
		return userBO;
	}

	@Override
	public UserBO findByCode(final int idSchool, final String code,
			final int identifier) {
		UserBO user = this.getUserFromCacheUsingDocNumber(code);
		if (user == null) {
			user = this.selectByDocumentNumer(code);
		}
		return (user != null && user.isEnabled()) ? user : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserBO saveRecord(final UserBO record) {
		if (record.getId() == null || record.getId().equals(0)) {
			final UserBO cacheUser = this.findByCode(0, record.getDocumentNumber(), 0);
			if (cacheUser != null) {
				record.setId(cacheUser.getId());
			}
		}
		this.setPreviousData(record);
		record.setUpdated(new Date());
		final Bzuser bzUser = this.buildHibernateEntity(record);
		this.userDAO.save(bzUser);
		this.saveSchoolXUserRecords(bzUser.getId(), bzUser.getBzschoolxusers());
		this.saveUserXUserTypeRecords(bzUser.getId(),
				bzUser.getBzuserxusertypes());
		return this.putObjectInCache(bzUser);
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

	public UserBO getUserFromCacheUsingDocNumber(final String documentNumber) {
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
				schoolId, yearBO.getName(), null, null, false);
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
		if (userBO != null) {
			for (final SchoolBO school : userBO.getSchoolSet()) {
				if (school.getId().equals(schoolId)) {
					linked = true;
					break;
				}
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
			if (userBO.getGuardian1() != null) {
				final UserBO guardian = this.saveRecord(userBO.getGuardian1());
				bzUser.setBzuserByIdGuardian1(this.buildHibernateEntity(guardian));
			}
			if (userBO.getGuardian2() != null) {
				final UserBO guardian = this.saveRecord(userBO.getGuardian2());
				bzUser.setBzuserByIdGuardian2(this.buildHibernateEntity(guardian));
			}
			bzUser.setCreation(userBO.getCreation());
			bzUser.setUpdated(userBO.getUpdated());
			bzUser.setEnabled(userBO.isEnabled());
			if (userBO.getSchoolSet() != null) {
				Set<Bzschoolxuser> bzschoolxusers = new HashSet<>();
				for (final SchoolBO school : userBO.getSchoolSet()) {
					bzschoolxusers.add(this.buildSchoolXUserHibernateEntity(
							school, bzUser));
				}
				bzUser.setBzschoolxusers(bzschoolxusers);
			}
			if (userBO.getUserTypeSet() != null) {
				Set<Bzuserxusertype> bzuserxusertypes = new HashSet<>();
				for (final UserTypeBO userType : userBO.getUserTypeSet()) {
					bzuserxusertypes
							.add(this.buildUserXUserTypeHibernateEntity(
									userType, bzUser));
				}
				bzUser.setBzuserxusertypes(bzuserxusertypes);
			}
		}
		return bzUser;
	}

	@Override
	public UserBO putObjectInCache(Bzuser record) {
		final Bzuser queryResult = this.userDAO.selectByIdentifier(record
				.getId());
		final UserBO userBO = this.buildUserBO(queryResult, true);
		this.putObjectInCache(USER_KEY, userBO);
		return userBO;
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
			if (bzClassRoomXuser.isEnabled()) {
				final UserBO user = this.buildUserBO(
						bzClassRoomXuser.getBzuser(), false);
				if (user.isEnabled()) {
					userSet.add(user);
				}
			}
		}
		return userSet;
	}

	public Set<UserBO> findStudentsNotLinkedToClassRoom(final int idSchool,
			final Integer grade, final Integer idClassRoom) {
		final Set<UserBO> userSet = new HashSet<>();
		final Set<UserBO> cacheUserSet = this.getObjectsFromCache();
		final int lastYear = yearBLL.findLastYear();
		final YearBO year = yearBLL.findCurrentYear();
		final Set<ClassRoomBO> classRoomLastYearSet = classRoomBLL.findBy(
				idClassRoom, idSchool, String.valueOf(lastYear), grade, null, false);
		final Set<ClassRoomBO> classRoomCurrentYearSet = classRoomBLL.findBy(
				null, idSchool, year.getName(), null, null, false);
		final UserTypeBO userType = userTypeBLL.findByCode(idSchool,
				UserTypeBO.STUDENT, 0);
		if (cacheUserSet != null) {
			for (UserBO user : cacheUserSet) {
				if (this.isLinkedToSchool(user, idSchool)
						&& this.isLinkedToUserType(user, userType)
						&& !this.isStudentLinkedToClassRoom(
								classRoomCurrentYearSet, user)
						&& ((idClassRoom == null && grade == null) || this
								.isStudentLinkedToClassRoom(
										classRoomLastYearSet, user))) {
					final ClassRoomBO lastClassRoom = this.findLastClassRoom(
							classRoomLastYearSet, user);
					user.setLastClassRoom(lastClassRoom);
					userSet.add(user);
				}
			}
		}
		return userSet;
	}

	private boolean isStudentLinkedToClassRoom(
			final Set<ClassRoomBO> classRoomSet, final UserBO user) {
		boolean isLinked = false;
		if (classRoomSet != null) {
			for (final ClassRoomBO classRoom : classRoomSet) {
				for (final UserBO studentInClassRoom : classRoom
						.getStudentSet()) {
					if (studentInClassRoom.equals(user)) {
						isLinked = true;
						break;
					}
				}
			}
		}
		return isLinked;
	}

	private ClassRoomBO findLastClassRoom(final Set<ClassRoomBO> classRoomSet,
			final UserBO user) {
		ClassRoomBO lastClassRoom = null;
		if (classRoomSet != null) {
			for (final ClassRoomBO classRoom : classRoomSet) {
				for (final UserBO studentInClassRoom : classRoom
						.getStudentSet()) {
					if (studentInClassRoom.equals(user)) {
						lastClassRoom = classRoom;
						break;
					}
				}
			}
		}
		return lastClassRoom;
	}

	private void setPreviousData(final UserBO record) {
		if (record.getId() == null || record.getId() == 0) {
			record.setPassword("123456");
			record.setCreation(new Date());
		} else {
			final UserBO cacheUser = (UserBO) this.getObjectFromCache(USER_KEY,
					record.getId());
			record.setPassword(cacheUser.getPassword());
			record.setCreation(cacheUser.getCreation());
			if (record.getPhoto() == null && cacheUser.getPhoto() != null) {
				record.setPhoto(cacheUser.getPhoto());
			}
		}
	}

	private Bzschoolxuser buildSchoolXUserHibernateEntity(
			final SchoolBO school, final Bzuser bzUser) {
		final BzschoolxuserId bzSchoolxUserId = new BzschoolxuserId(
				school.getId(), bzUser.getId());
		final Bzschool bzSchool = schoolBLL.buildHibernateEntity(school);
		final Bzschoolxuser bzSchoolxUser = new Bzschoolxuser(bzSchoolxUserId,
				bzSchool, bzUser, new Date(), new Date(), school.isEnabled());
		return bzSchoolxUser;
	}

	private Bzuserxusertype buildUserXUserTypeHibernateEntity(
			final UserTypeBO userType, final Bzuser bzUser) {
		final BzuserxusertypeId bzUserXUserTypeId = new BzuserxusertypeId(
				bzUser.getId(), userType.getId());
		final Cnusertype cnUserType = userTypeBLL
				.buildHibernateEntity(userType);
		final Bzuserxusertype bzUserxUserType = new Bzuserxusertype(
				bzUserXUserTypeId, bzUser, cnUserType, new Date(), new Date(),
				true);
		return bzUserxUserType;
	}

	private void saveSchoolXUserRecords(final int idUser,
			final Set<Bzschoolxuser> bzSchoolxUserSet) {
		for (final Bzschoolxuser bzSchoolxUser : bzSchoolxUserSet) {
			bzSchoolxUser.getId().setIdUser(idUser);
			this.schoolxUserDAO.save(bzSchoolxUser);
		}
	}

	private void saveUserXUserTypeRecords(final int idUser,
			final Set<Bzuserxusertype> bzUserxUserTypeSet) {
		for (final Bzuserxusertype bzUserxUserType : bzUserxUserTypeSet) {
			bzUserxUserType.getId().setIdUser(idUser);
			this.userxUserTypeDAO.save(bzUserxUserType);
		}
	}
}