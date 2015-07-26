/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.carpcosoftware.schoolmanagement.dao.UserTypeDAO;
import co.com.carpcosoftware.schoolmanagement.entity.UserTypeBO;
import co.com.carpcosoftware.schoolmanagement.hibernate.Cnusertype;
import co.com.carpcosoftware.schoolmanagement.util.CacheManager;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Service
public class UserTypeBLL implements IBusinessLogicLayer<UserTypeBO> {

	private static final String CACHE_KEY = "userTypes";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserTypeBLL.class);

	@Autowired
	private UserTypeDAO userTypeDAO;

	@Autowired
	protected CacheManager cacheManager;
	
	@Override
	public Set<UserTypeBO> findAll() {
		return this.cacheManager.isCacheEmpty(CACHE_KEY) ? this
				.selectAndPutInCache() : this.getObjectsFromCache();
	}

	@Override
	public UserTypeBO findByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserTypeBO findByCode(String code) {
		UserTypeBO userTypeBO = this.getUserTypeBOFromCache(code);
		if (userTypeBO == null) {
			userTypeBO = this.selectByCode(code);
		}
		return userTypeBO;
	}

	@Override
	public boolean insertRecord(UserTypeBO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(UserTypeBO record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<UserTypeBO> selectAndPutInCache() {
		Set<Cnusertype> cnUserTypeSet = this.userTypeDAO.select();
		Set<UserTypeBO> userTypeBOSet = this
				.createEntityBOSetUsingHibernatEntities(cnUserTypeSet);
		if (userTypeBOSet != null) {
			this.cacheManager.putObjectsInCache(
					this.cacheManager.getCache(CACHE_KEY), userTypeBOSet);
		}
		return userTypeBOSet;
	}

	@Override
	public UserTypeBO selectByIdentifierAndPutInCache(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<UserTypeBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<UserTypeBO> userTypeBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			userTypeBOSet = new HashSet<>();
			for (Object cnUserType : hibernateEntitySet) {
				if (cnUserType instanceof Cnusertype) {
					userTypeBOSet.add(new UserTypeBO(
							(Cnusertype) cnUserType));
				}
			}
		}
		return userTypeBOSet;
	}
	
	@SuppressWarnings("unchecked")
	private Set<UserTypeBO> getObjectsFromCache() {
		return this.cacheManager.getObjectsFromCache(this.cacheManager
				.getCache(CACHE_KEY));
	}
	
	public UserTypeBO getUserTypeBOFromCache(String code) {
		UserTypeBO userTypeBO = null;
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		for (Object key : cache.getKeys()) {
			Object objectFromCache = this.cacheManager.getObjectFromCache(cache, (Integer) key);
			if (objectFromCache != null
					&& ((UserTypeBO) objectFromCache).getCode().equals(code)) {
				userTypeBO = (UserTypeBO) objectFromCache;
				break;
			}
		}
		return userTypeBO;
	}
	
	public UserTypeBO selectByCode(String code) {
		UserTypeBO userTypeBO = null;
		Cnusertype cnuserType = this.userTypeDAO.selectByCode(code);
		if (cnuserType != null) {
			userTypeBO = new UserTypeBO(cnuserType);
			this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), userTypeBO);
		}
		return userTypeBO;
	}

}
