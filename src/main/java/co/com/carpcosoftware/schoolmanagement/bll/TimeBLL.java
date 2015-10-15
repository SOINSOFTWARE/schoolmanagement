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

import co.com.carpcosoftware.schoolmanagement.dao.TimeDAO;
import co.com.carpcosoftware.schoolmanagement.entity.TimeBO;
import co.com.carpcosoftware.schoolmanagement.hibernate.Bztime;
import co.com.carpcosoftware.schoolmanagement.util.CacheManager;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 27/08/2015
 */
@Service
public class TimeBLL implements IBusinessLogicLayer<TimeBO> {
	
	private static final String CACHE_KEY = "times";

	private static final Logger LOGGER = LoggerFactory	
			.getLogger(TimeBLL.class);
	
	@Autowired
	private TimeDAO timeDAO;

	@Autowired
	protected CacheManager cacheManager;

	@Override
	public Set<TimeBO> findAll() {
		return this.selectAll();
	}

	@Override
	public TimeBO findByIdentifier(Integer identifier) {
		TimeBO timeBO = null;
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		if (cache.getSize() == 0) {
			timeBO = this.selectByIdentifierAndPutInCache(identifier);
		} else {
			timeBO = (TimeBO) this.cacheManager.getObjectFromCache(cache, identifier);
		}
		LOGGER.info("Time = {} was loaded successfully", timeBO.toString());
		return timeBO;
	}

	@Override
	public TimeBO findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean saveRecord(TimeBO record) {
		return record.getId() == 0 ? 
				this.insertRecord(record) : 
				this.updateRecord(record);
	}

	@Override
	public boolean insertRecord(TimeBO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(TimeBO record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<TimeBO> selectAndPutInCache() {
		Set<Bztime> bzTimeSet = this.timeDAO.select();
		Set<TimeBO> timeBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzTimeSet);
		if (timeBOSet != null) {
			this.cacheManager.putObjectsInCache(
					this.cacheManager.getCache(CACHE_KEY), timeBOSet);
		}
		return timeBOSet;
	}

	@Override
	public TimeBO selectByIdentifierAndPutInCache(Integer identifier) {
		TimeBO timeBO = null;
		Bztime bzTime = this.timeDAO.selectByIdentifier(identifier);
		if (bzTime != null) {
			timeBO = new TimeBO(bzTime);
			this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), timeBO);
		}
		return timeBO;
	}

	@Override
	public Set<TimeBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<TimeBO> timeBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			timeBOSet = new HashSet<>();
			for (Object bzTime : hibernateEntitySet) {
				if (bzTime instanceof Bztime) {
					timeBOSet.add(new TimeBO(
							(Bztime) bzTime));
				}
			}	
		}
		return timeBOSet;
	}
	
	private Set<TimeBO> selectAll() {
		return this.cacheManager.isCacheEmpty(CACHE_KEY) ? this.selectAndPutInCache() : this.getObjectsFromCache();
	}
	
	@SuppressWarnings("unchecked")
	private Set<TimeBO> getObjectsFromCache() {
		LOGGER.info("Loading times entities from cache");
		return this.cacheManager.getObjectsFromCache(this.cacheManager
				.getCache(CACHE_KEY));
	}
	
	public Bztime buildTimeHibernateEntity(TimeBO timeBO) {
		Bztime bzTime = new Bztime();
		bzTime.setId(timeBO.getId());
		bzTime.setCode(timeBO.getCode());
		bzTime.setName(timeBO.getName());
		bzTime.setCreation(timeBO.getCreation());
		bzTime.setUpdated(timeBO.getUpdated());
		bzTime.setEnabled(timeBO.isEnabled());
		return bzTime;
	}

}
