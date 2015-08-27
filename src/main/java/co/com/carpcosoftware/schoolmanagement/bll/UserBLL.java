package co.com.carpcosoftware.schoolmanagement.bll;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.carpcosoftware.schoolmanagement.dao.UserDAO;
import co.com.carpcosoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.carpcosoftware.schoolmanagement.entity.UserBO;
import co.com.carpcosoftware.schoolmanagement.entity.UserTypeBO;
import co.com.carpcosoftware.schoolmanagement.entity.YearBO;
import co.com.carpcosoftware.schoolmanagement.hibernate.Bzuser;
import co.com.carpcosoftware.schoolmanagement.util.CacheManager;
import co.com.carpcosoftware.schoolmanagement.util.ImageUtil;

/**
 * User business logic layer
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 16/04/2015
 */
@Service
public class UserBLL implements IBusinessLogicLayer<UserBO> {
	
	private static final String USER_TYPE_TEACHER = "PROFE";
	
	private static final String CACHE_KEY = "users";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserBLL.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	protected CacheManager cacheManager;
	
	@Autowired
	private UserTypeBLL userTypeBLL;
	
	@Autowired
	private ClassRoomBLL classRoomBLL;
	
	@Autowired
	private YearBLL yearBLL;

	@SuppressWarnings("unchecked")
	@Override
	public Set<UserBO> findAll() {
		Set<UserBO> userBOSet = null;
		if (this.cacheManager.isCacheEmpty(CACHE_KEY)) {
			userBOSet = this.selectAndPutInCache();
		} else {
			userBOSet = this.cacheManager
					.getObjectsFromCache(this.cacheManager.getCache(CACHE_KEY));
		}
		return userBOSet;
	}

	@Override
	public UserBO findByIdentifier(Integer identifier) {
		UserBO userBO = null;
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		if (cache.getSize() == 0) {
			userBO = this.selectByIdentifierAndPutInCache(identifier);
		} else {
			userBO = (UserBO) this.cacheManager.getObjectFromCache(cache, identifier);
		}
		LOGGER.info("User = {} was loaded successfully", userBO.toString());
		return userBO;
	}

	@Override
	public UserBO findByCode(String code) {
		UserBO userBO = this.getUserBOFromCache(code);
		if (userBO == null) {
			userBO = this.selectByDocumentNumer(code);
		}
		return userBO;
	}

	@Override
	public boolean insertRecord(UserBO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(UserBO record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<UserBO> selectAndPutInCache() {
		Set<Bzuser> bzUserSet = this.userDAO.select();
		Set<UserBO> userBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzUserSet);
		if (userBOSet != null) {
			this.cacheManager.putObjectsInCache(
					this.cacheManager.getCache(CACHE_KEY), userBOSet);
		}
		return userBOSet;
	}
	
	@Override
	public Set<UserBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<UserBO> userBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			userBOSet = new HashSet<>();
			for (Object bzUser : hibernateEntitySet) {
				if (bzUser instanceof Bzuser) {
					userBOSet.add(new UserBO((Bzuser)bzUser));
				}
			}
		}
		return userBOSet;
	}

	@Override
	public UserBO selectByIdentifierAndPutInCache(Integer identifier) {
		UserBO userBO = null;
		Bzuser bzUser = this.userDAO.selectByIdentifier(identifier);
		if (bzUser != null) {
			userBO = new UserBO(bzUser);
			this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), userBO);
		}
		return userBO;
	}
	
	public UserBO selectByDocumentNumer(String documentNumber) {
		UserBO userBO = null;
		Bzuser bzUser = this.userDAO.selectByDocumentNumber(documentNumber);
		if (bzUser != null) {
			userBO = new UserBO(bzUser);
			this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), userBO);
		}
		return userBO;
	}
	
	public UserBO getUserBOFromCache(String documentNumber) {
		UserBO userBO = null;
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		for (Object key : cache.getKeys()) {
			Object objectFromCache = this.cacheManager.getObjectFromCache(cache, (Integer) key);
			if (objectFromCache != null
					&& ((UserBO) objectFromCache).getDocumentNumber().equals(documentNumber)) {
				userBO = (UserBO) objectFromCache;
				break;
			}
		}
		return userBO;
	}
	
	public Set<UserBO> findByType(int schoolId, String userTypeCode){
		Set<UserBO> userBOSet = new HashSet<>();
		Set<UserBO> cacheUserBOSet = this.getObjectsFromCache();
		UserTypeBO userTypeBO = userTypeBLL.findByCode(userTypeCode);
		if (cacheUserBOSet != null) {
			for(UserBO userBO : cacheUserBOSet) {
				if (this.isUserLinkedToSchool(userBO, schoolId) && 
						this.isUserLinkedToUserType(userBO, userTypeBO)) {
					userBOSet.add(userBO);
				}
			}
		}
		return userBOSet;
	}
	
	public Set<UserBO> findTeacherNoDirectors(int schoolId){
		Set<UserBO> userBOSet = new HashSet<>();
		Set<UserBO> cacheUserBOSet = this.getObjectsFromCache();
		YearBO yearBO = yearBLL.findCurrentYear();
		Set<ClassRoomBO> classRoomBOSet = classRoomBLL.findBy(null, schoolId, yearBO.getName(), null, null);
		UserTypeBO userTypeBO = userTypeBLL.findByCode(USER_TYPE_TEACHER);
		if (cacheUserBOSet != null) {
			for(UserBO userBO : cacheUserBOSet) {
				if (this.isUserLinkedToSchool(userBO, schoolId) 
						&& this.isUserLinkedToUserType(userBO, userTypeBO)
						&& !this.isTeacherADirector(classRoomBOSet, userBO)) {
					userBOSet.add(userBO);
					LOGGER.info("Teacher that is not a director yet : {}", userBO.toString());
				}
			}
		}
		return userBOSet;
	}
	
	private boolean isUserLinkedToSchool(UserBO userBO, int schoolId) {
		return userBO.getSchool().getId().equals(schoolId);
	}
	
	private boolean isUserLinkedToUserType(UserBO userBO, UserTypeBO userTypeBO) {
		return userBO.getUserTypeSet().contains(userTypeBO);
	}
	
	private boolean isTeacherADirector(Set<ClassRoomBO> classRoomBOSet, UserBO userBO)
	{
		boolean isDirector = false;
		if (classRoomBOSet != null) {
			for(ClassRoomBO classRoomBO : classRoomBOSet) {
				if (classRoomBO.getUserBO().equals(userBO)) {
					isDirector = true;
					break;
				}
			}
		}
		return isDirector;
	}
	
	@SuppressWarnings("unchecked")
	private Set<UserBO> getObjectsFromCache() {
		return this.cacheManager.getObjectsFromCache(this.cacheManager
				.getCache(CACHE_KEY));
	}

	public Bzuser buildUserHibernateEntity(UserBO userBO) {
		Bzuser bzUser = null;
		if (userBO != null) {
			bzUser = new Bzuser();
			bzUser.setId(userBO.getId());
			bzUser.setDocumentNumber(userBO.getDocumentNumber());
			bzUser.setDocumentType(userBO.getDocumentType());
			bzUser.setName(userBO.getName());
			bzUser.setLastName(userBO.getLastName());
			bzUser.setBorn(userBO.getBorn());
			bzUser.setAddress(userBO.getAddress());
			bzUser.setPhone1(userBO.getPhone1().longValue());
			bzUser.setPhone2(userBO.getPhone2() != null ? userBO.getPhone2().longValue() : null);
			bzUser.setPassword(userBO.getPassword());
			bzUser.setGender(userBO.getGender());
			bzUser.setPhoto(ImageUtil.encodeToByteArray((BufferedImage) userBO.getPhoto()));
			bzUser.setBzuserByIdGuardian1(this.buildUserHibernateEntity(userBO.getGuardian1()));
			bzUser.setBzuserByIdGuardian2(this.buildUserHibernateEntity(userBO.getGuardian2()));
			bzUser.setCreation(userBO.getCreation());
			bzUser.setUpdated(userBO.getUpdated());
			bzUser.setEnabled(userBO.isEnabled());
		}
		return bzUser;
	}
}
