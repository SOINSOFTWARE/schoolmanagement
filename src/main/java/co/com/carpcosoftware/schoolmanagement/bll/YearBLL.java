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

import co.com.carpcosoftware.schoolmanagement.dao.YearDAO;
import co.com.carpcosoftware.schoolmanagement.entity.YearBO;
import co.com.carpcosoftware.schoolmanagement.hibernate.Bzyear;
import co.com.carpcosoftware.schoolmanagement.util.CacheManager;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
@Service
public class YearBLL implements IBusinessLogicLayer<YearBO> {
	
	private static final String CACHE_KEY = "years";

	private static final Logger LOGGER = LoggerFactory	
			.getLogger(YearBLL.class);

	@Autowired
	private YearDAO yearDAO;

	@Autowired
	protected CacheManager cacheManager;		

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#findAll()
	 */
	@Override
	public Set<YearBO> findAll() {
		return this.selectAll();
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#findByIdentifier(java.lang.Integer)
	 */
	@Override
	public YearBO findByIdentifier(Integer identifier) {
		YearBO yearBO = null;
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		if (cache.getSize() == 0) {
			yearBO = this.selectByIdentifierAndPutInCache(identifier);
		} else {
			yearBO = (YearBO) this.cacheManager.getObjectFromCache(cache, identifier);
		}
		LOGGER.info("Year = {} was loaded successfully", yearBO.toString());
		return yearBO;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#findByCode(java.lang.String)
	 */
	@Override
	public YearBO findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean saveRecord(YearBO record) {
		return record.getId() == 0 ? 
				this.insertRecord(record) : 
				this.updateRecord(record);
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#insertRecord(java.lang.Object)
	 */
	@Override
	public boolean insertRecord(YearBO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#updateRecord(java.lang.Object)
	 */
	@Override
	public boolean updateRecord(YearBO record) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#selectAndPutInCache()
	 */
	@Override
	public Set<YearBO> selectAndPutInCache() {
		Set<Bzyear> bzYearSet = this.yearDAO.select();
		Set<YearBO> YearBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzYearSet);
		if (YearBOSet != null) {
			this.cacheManager.putObjectsInCache(
					this.cacheManager.getCache(CACHE_KEY), YearBOSet);
		}
		return YearBOSet;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#selectByIdentifierAndPutInCache(java.lang.Integer)
	 */
	@Override
	public YearBO selectByIdentifierAndPutInCache(Integer identifier) {
		YearBO yearBO = null;
		Bzyear bzYear = this.yearDAO.selectByIdentifier(identifier);
		if (bzYear != null) {
			yearBO = new YearBO(bzYear);
			this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), yearBO);
		}
		return yearBO;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#createEntityBOSetUsingHibernatEntities(java.util.Set)
	 */
	@Override
	public Set<YearBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<YearBO> yearBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			yearBOSet = new HashSet<>();
			for (Object bzYear : hibernateEntitySet) {
				if (bzYear instanceof Bzyear) {
					yearBOSet.add(new YearBO(
							(Bzyear) bzYear));
				}
			}	
		}
		return yearBOSet;
	}
	
	@SuppressWarnings("unchecked")
	private Set<YearBO> getObjectsFromCache() {
		return this.cacheManager.getObjectsFromCache(this.cacheManager
				.getCache(CACHE_KEY));
	}
	
	public YearBO findCurrentYear() {
		YearBO currentYear = null;
		Set<YearBO> yearBOSet = this.selectAll();
		for(YearBO year : yearBOSet) {
			if (year.isEnabled()) {
				currentYear = year;
				LOGGER.info("Current year loaded: {}", currentYear.toString());
				break;
			}
		}
		return currentYear;
	}
	
	private Set<YearBO> selectAll() {
		return this.cacheManager.isCacheEmpty(CACHE_KEY) ? this.selectAndPutInCache() : this.getObjectsFromCache();
	}

	public Bzyear buildYearHibernateEntity(YearBO yearBO) {
		Bzyear bzYear = new Bzyear();
		bzYear.setId(yearBO.getId());
		bzYear.setName(yearBO.getName());
		bzYear.setCreation(yearBO.getCreation());
		bzYear.setUpdated(yearBO.getUpdated());
		bzYear.setEnabled(yearBO.isEnabled());
		return bzYear;
	}

}
