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
import co.com.soinsoftware.schoolmanagement.entity.AccessBO;
import co.com.soinsoftware.schoolmanagement.entity.UserTypeBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzuserxusertype;
import co.com.soinsoftware.schoolmanagement.hibernate.Cnusertype;
import co.com.soinsoftware.schoolmanagement.hibernate.Cnusertypexaccess;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Service
public class UserTypeBLL extends AbstractBLL implements
		IBusinessLogicLayer<UserTypeBO, Cnusertype> {

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
					Set<AccessBO> accessSet = this.getAccessSet((Cnusertype) cnUserType);
					userTypeBOSet.add(new UserTypeBO((Cnusertype) cnUserType,
							accessSet));
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
			Set<AccessBO> accessSet = this.getAccessSet(cnuserType);
			userTypeBO = new UserTypeBO(cnuserType, accessSet);
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
	public UserTypeBO putObjectInCache(Cnusertype record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cnusertype buildHibernateEntity(UserTypeBO record) {
		Cnusertype cnUserType = new Cnusertype();
		if (record != null) {
			cnUserType.setId(record.getId());
			cnUserType.setCode(record.getCode());
			cnUserType.setName(record.getName());
			cnUserType.setCreation(record.getCreation());
			cnUserType.setUpdated(record.getUpdated());
			cnUserType.setEnabled(record.isEnabled());
		}
		return cnUserType;
	}

	@SuppressWarnings("unchecked")
	private Set<AccessBO> getAccessSet(final Cnusertype cnuserType) {
		final Set<AccessBO> accessSet = new HashSet<>();
		if (cnuserType.getCnusertypexaccesses() != null) {
			cnuserType
					.getCnusertypexaccesses()
					.stream()
					.forEach(
							(cnUserTypeXAccess) -> {
								accessSet.add(new AccessBO(
										((Cnusertypexaccess) cnUserTypeXAccess)
												.getCnaccess()));
							});
		}
		return accessSet;
	}
	
	@SuppressWarnings("unchecked")
	public Set<UserTypeBO> buildUserTypeSet(
			final Set<Bzuserxusertype> bzUserxUserTypes) {
		final Set<UserTypeBO> userTypeSet = new HashSet<>();
		for (final Bzuserxusertype userXuserType : bzUserxUserTypes) {
			final Cnusertype cnUserType = userXuserType.getCnusertype();
			final Set<Cnusertypexaccess> userTypeXaccessSet = cnUserType
					.getCnusertypexaccesses();
			final Set<AccessBO> accessSet = new HashSet<>();
			for (final Cnusertypexaccess usertypeXaccess : userTypeXaccessSet) {
				final AccessBO access = new AccessBO(
						usertypeXaccess.getCnaccess());
				accessSet.add(access);
			}
			final UserTypeBO userType = new UserTypeBO(cnUserType, accessSet);
			userTypeSet.add(userType);
		}
		return userTypeSet;
	}
}