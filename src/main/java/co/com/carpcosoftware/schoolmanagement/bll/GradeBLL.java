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

import co.com.carpcosoftware.schoolmanagement.dao.GradeDAO;
import co.com.carpcosoftware.schoolmanagement.entity.GradeBO;
import co.com.carpcosoftware.schoolmanagement.hibernate.Bzgrade;
import co.com.carpcosoftware.schoolmanagement.util.CacheManager;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
@Service
public class GradeBLL implements IBusinessLogicLayer<GradeBO> {
	
	private static final String CACHE_KEY = "grades";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GradeBLL.class);

	@Autowired
	private GradeDAO gradeDAO;

	@Autowired
	protected CacheManager cacheManager;

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#findAll()
	 */
	@Override
	public Set<GradeBO> findAll() {
		return this.cacheManager.isCacheEmpty(CACHE_KEY) ? this
				.selectAndPutInCache() : this.getObjectsFromCache();
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#findByIdentifier(java.lang.Integer)
	 */
	@Override
	public GradeBO findByIdentifier(Integer identifier) {
		GradeBO gradeBO = null;
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		if (cache.getSize() == 0) {
			gradeBO = this.selectByIdentifierAndPutInCache(identifier);
		} else {
			gradeBO = (GradeBO) this.cacheManager.getObjectFromCache(cache, identifier);
		}
		LOGGER.info("Grade = {} was loaded successfully", gradeBO.toString());
		return gradeBO;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#findByCode(java.lang.String)
	 */
	@Override
	public GradeBO findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean saveRecord(GradeBO record) {
		return record.getId() == 0 ? 
				this.insertRecord(record) : 
				this.updateRecord(record);
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#insertRecord(java.lang.Object)
	 */
	@Override
	public boolean insertRecord(GradeBO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#updateRecord(java.lang.Object)
	 */
	@Override
	public boolean updateRecord(GradeBO record) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#selectAndPutInCache()
	 */
	@Override
	public Set<GradeBO> selectAndPutInCache() {
		Set<Bzgrade> bzGradeSet = this.gradeDAO.select();
		Set<GradeBO> gradeBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzGradeSet);
		if (gradeBOSet != null) {
			this.cacheManager.putObjectsInCache(
					this.cacheManager.getCache(CACHE_KEY), gradeBOSet);
		}
		return gradeBOSet;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#selectByIdentifierAndPutInCache(java.lang.Integer)
	 */
	@Override
	public GradeBO selectByIdentifierAndPutInCache(Integer identifier) {
		GradeBO gradeBO = null;
		Bzgrade bzGrade = this.gradeDAO.selectByIdentifier(identifier);
		if (bzGrade != null) {
			gradeBO = new GradeBO(bzGrade);
			this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), gradeBO);
		}
		return gradeBO;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#createEntityBOSetUsingHibernatEntities(java.util.Set)
	 */
	@Override
	public Set<GradeBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<GradeBO> gradeBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			gradeBOSet = new HashSet<>();
			for (Object bzGrade : hibernateEntitySet) {
				if (bzGrade instanceof Bzgrade) {
					gradeBOSet.add(new GradeBO(
							(Bzgrade) bzGrade));
				}
			}	
		}
		return gradeBOSet;
	}
	
	@SuppressWarnings("unchecked")
	private Set<GradeBO> getObjectsFromCache() {
		return this.cacheManager.getObjectsFromCache(this.cacheManager
				.getCache(CACHE_KEY));
	}

	public Bzgrade buildGradeHibernateEntity(GradeBO gradeBO) {
		Bzgrade bzGrade = new Bzgrade();
		bzGrade.setId(gradeBO.getId());
		bzGrade.setCode(gradeBO.getCode());
		bzGrade.setName(gradeBO.getName());
		bzGrade.setCreation(gradeBO.getCreation());
		bzGrade.setUpdated(gradeBO.getUpdated());
		bzGrade.setEnabled(gradeBO.isEnabled());
		return bzGrade;
	}

}
