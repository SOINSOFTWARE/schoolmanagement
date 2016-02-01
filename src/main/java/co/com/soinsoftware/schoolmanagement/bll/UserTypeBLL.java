/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.UserTypeDAO;
import co.com.soinsoftware.schoolmanagement.entity.UserTypeBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzuserxusertype;
import co.com.soinsoftware.schoolmanagement.hibernate.Cnusertype;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Service
public class UserTypeBLL extends AbstractBLL implements
		IBusinessLogicLayer<UserTypeBO, Bzuserxusertype> {

	@Autowired
	private UserTypeDAO userTypeDAO;

	@Override
	public Set<UserTypeBO> findAll() {
		return this.isCacheEmpty(USERTYPE_KEY) ? this.selectAndPutInCache()
				: this.getObjectsFromCache();
	}

	@Override
	public Set<UserTypeBO> findAll(final int idSchool) {
		return this.isCacheEmpty(USERTYPE_KEY) ? this.selectAndPutInCache()
				: this.getObjectsFromCache();
	}

	@Override
	public UserTypeBO findByIdentifier(final Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserTypeBO findByCode(final int idSchool, final String code,
			final int identifier) {
		UserTypeBO userTypeBO = this.getUserTypeBOFromCache(code);
		if (userTypeBO == null) {
			userTypeBO = this.selectByCode(code);
		}
		return userTypeBO;
	}

	@Override
	public UserTypeBO saveRecord(final UserTypeBO record) {
		return null;
	}

	@Override
	public Set<UserTypeBO> selectAndPutInCache() {
		final Set<Cnusertype> cnUserTypeSet = this.userTypeDAO.select();
		final Set<UserTypeBO> userTypeBOSet = this
				.createEntityBOSetUsingHibernatEntities(cnUserTypeSet);
		if (userTypeBOSet != null) {
			this.putObjectsInCache(USERTYPE_KEY, userTypeBOSet);
		}
		return userTypeBOSet;
	}

	@Override
	public UserTypeBO selectByIdentifierAndPutInCache(final Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<UserTypeBO> createEntityBOSetUsingHibernatEntities(
			final Set<?> hibernateEntitySet) {
		Set<UserTypeBO> userTypeBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			userTypeBOSet = new HashSet<>();
			for (Object cnUserType : hibernateEntitySet) {
				if (cnUserType instanceof Cnusertype) {
					userTypeBOSet.add(new UserTypeBO((Cnusertype) cnUserType));
				}
			}
		}
		return userTypeBOSet;
	}

	@SuppressWarnings("unchecked")
	protected Set<UserTypeBO> getObjectsFromCache() {
		return this.getObjectsFromCache(USERTYPE_KEY);
	}

	public UserTypeBO getUserTypeBOFromCache(String code) {
		UserTypeBO userTypeBO = null;
		final Cache cache = this.getCacheUsingKey(USERTYPE_KEY);
		for (Object key : cache.getKeys()) {
			final Object objectFromCache = this.getObjectFromCache(
					USERTYPE_KEY, (Integer) key);
			if (objectFromCache != null
					&& ((UserTypeBO) objectFromCache).getCode().equals(code)) {
				userTypeBO = (UserTypeBO) objectFromCache;
				break;
			}
		}
		return userTypeBO;
	}

	public UserTypeBO selectByCode(final String code) {
		UserTypeBO userTypeBO = null;
		final Cnusertype cnuserType = this.userTypeDAO.selectByCode(code);
		if (cnuserType != null) {
			userTypeBO = new UserTypeBO(cnuserType);
			this.putObjectInCache(USERTYPE_KEY, userTypeBO);
		}
		return userTypeBO;
	}

	public boolean isValidUserType(final String userType) {
		return userType != null
				&& (userType.equals(UserTypeBO.COORDINATOR)
						|| userType.equals(UserTypeBO.DIRECTOR)
						|| userType.equals(UserTypeBO.GUARDIAN)
						|| userType.equals(UserTypeBO.STUDENT) || userType
							.equals(UserTypeBO.TEACHER));
	}

	@Override
	public UserTypeBO putObjectInCache(Bzuserxusertype record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bzuserxusertype buildHibernateEntity(UserTypeBO record) {
		// TODO Auto-generated method stub
		return null;
	}
}