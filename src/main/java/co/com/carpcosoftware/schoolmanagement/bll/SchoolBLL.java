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

import co.com.carpcosoftware.schoolmanagement.dao.SchoolDAO;
import co.com.carpcosoftware.schoolmanagement.entity.SchoolBO;
import co.com.carpcosoftware.schoolmanagement.hibernate.Bzschool;
import co.com.carpcosoftware.schoolmanagement.util.CacheManager;

/**
 * School business logic layer
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
@Service
public class SchoolBLL implements IBusinessLogicLayer<SchoolBO> {

	/**
	 * Cache key
	 */
	private static final String CACHE_KEY = "schools";

	/**
	 * Logger instance
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SchoolBLL.class);

	/**
	 * {@link SchoolDAO} that will provide access to database table
	 */
	@Autowired
	private SchoolDAO schoolDAO;

	/**
	 * {@link CacheManager} that will provide access to cache objects
	 */
	@Autowired
	protected CacheManager cacheManager;

	@SuppressWarnings("unchecked")
	@Override
	public Set<SchoolBO> findAll() {
		Set<SchoolBO> schoolBOSet = null;
		if (this.cacheManager.isCacheEmpty(CACHE_KEY)) {
			schoolBOSet = this.selectAndPutInCache();
		} else {
			schoolBOSet = this.cacheManager
					.getObjectsFromCache(this.cacheManager.getCache(CACHE_KEY));
		}
		return schoolBOSet;
	}

	@Override
	public SchoolBO findByIdentifier(Integer identifier) {
		SchoolBO schoolBO = null;
		if (this.cacheManager.isCacheEmpty(CACHE_KEY)) {
			schoolBO = this.selectByIdentifierAndPutInCache(identifier);
		} else {
			schoolBO = (SchoolBO) this.cacheManager.getObjectFromCache(
					this.cacheManager.getCache(CACHE_KEY), identifier);
			if (schoolBO == null) {
				schoolBO = this.selectByIdentifierAndPutInCache(identifier);
			}
		}
		return schoolBO;
	}

	@Override
	public SchoolBO findByCode(String code) {
		return (SchoolBO) this.cacheManager.getObjectFromCache(
				this.cacheManager.getCache(CACHE_KEY), code);
	}
	
	@Override
	public boolean saveRecord(SchoolBO record) {
		return record.getId() == 0 ? 
				this.insertRecord(record) : 
				this.updateRecord(record);
	}

	@Override
	public boolean insertRecord(SchoolBO newRecord) {
		boolean success = false;
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		if (this.cacheManager.getObjectFromCache(cache, newRecord.getCode()) == null) {
			// success = schoolDAO.insert(newRecord);
			if (success) {
				success = this.cacheManager.putObjectInCache(cache, newRecord);
			}
		} else {
			LOGGER.info("Code = {} already exist in database",
					newRecord.getCode());
		}

		return success;
	}

	@Override
	public boolean updateRecord(SchoolBO record) {
		boolean success = false;
		// boolean success = schoolDAO.update(record);
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		if (success) {
			success = this.cacheManager.putObjectInCache(cache, record);
		}
		return success;
	}

	@Override
	public Set<SchoolBO> selectAndPutInCache() {
		Set<Bzschool> bzSchoolSet = this.schoolDAO.select();
		Set<SchoolBO> schoolBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzSchoolSet);
		if (schoolBOSet != null) {
			this.cacheManager.putObjectsInCache(
					this.cacheManager.getCache(CACHE_KEY), schoolBOSet);
		}
		return schoolBOSet;
	}

	@Override
	public SchoolBO selectByIdentifierAndPutInCache(Integer identifier) {
		SchoolBO schoolBO = null;
		Bzschool bzSchool = this.schoolDAO.selectByIdentifier(identifier);
		if (bzSchool != null) {
			schoolBO = new SchoolBO(bzSchool);
			this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), schoolBO);
		}
		return schoolBO;
	}

	@Override
	public Set<SchoolBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<SchoolBO> schoolBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			schoolBOSet = new HashSet<>();
			for (Object bzSchool : hibernateEntitySet) {
				if (bzSchool instanceof Bzschool) {
					schoolBOSet.add(new SchoolBO((Bzschool)bzSchool));
				}
			}
		}
		return schoolBOSet;
	}

	public Bzschool buildSchoolHibernateEntity(SchoolBO schoolBO) {
		Bzschool bzSchool = new Bzschool();
		bzSchool.setId(schoolBO.getId());
		bzSchool.setCode(schoolBO.getCode());
		bzSchool.setName(schoolBO.getName());
		bzSchool.setCreation(schoolBO.getCreation());
		bzSchool.setUpdated(schoolBO.getUpdated());
		bzSchool.setEnabled(schoolBO.isEnabled());
		return bzSchool;
	}
}
