/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.carpcosoftware.schoolmanagement.dao.ClassRoomDAO;
import co.com.carpcosoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.carpcosoftware.schoolmanagement.hibernate.Bzclassroom;
import co.com.carpcosoftware.schoolmanagement.util.CacheManager;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Service
public class ClassRoomBLL implements IBusinessLogicLayer<ClassRoomBO> {

	private static final String CACHE_KEY = "classrooms";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClassRoomBLL.class);

	@Autowired
	private ClassRoomDAO classRoomDAO;
	
	@Autowired
	private GradeBLL gradeBLL;
	
	@Autowired
	private SchoolBLL schoolBLL;
	
	@Autowired
	private TimeBLL timeBLL;
	
	@Autowired
	private UserBLL userBLL;
	
	@Autowired
	private YearBLL yearBLL;

	@Autowired
	protected CacheManager cacheManager;

	@Override
	public Set<ClassRoomBO> findAll() {
		
		return this.cacheManager.isCacheEmpty(CACHE_KEY) ? this
				.selectAndPutInCache() : this.getObjectsFromCache();
	}

	@Override
	public ClassRoomBO findByIdentifier(Integer identifier) {
		ClassRoomBO classRoomBO = null;
		Cache cache = this.cacheManager.getCache(CACHE_KEY);
		if (cache.getSize() > 0) {
			classRoomBO = (ClassRoomBO) this.cacheManager.getObjectFromCache(cache, identifier);
		}
		if (classRoomBO == null) {
			classRoomBO = this.selectByIdentifierAndPutInCache(identifier);
		}
		if (classRoomBO != null) {
			LOGGER.info("classRoom = {} was loaded successfully", classRoomBO.toString());
		}
		return classRoomBO;
	}

	@Override
	public ClassRoomBO findByCode(String code) {
		return (ClassRoomBO) this.cacheManager.getObjectFromCache(
				this.cacheManager.getCache(CACHE_KEY), code);
	}
	
	@Override
	public boolean saveRecord(ClassRoomBO record) {
		return record.getId() == 0 ? 
				this.insertRecord(record) : 
				this.updateRecord(record);
	}

	@Override
	public boolean insertRecord(ClassRoomBO newRecord) {
		boolean success = false;
		newRecord.setCreation(DateTime.now().toDate());
		newRecord.setUpdated(DateTime.now().toDate());
		newRecord.setEnabled(true);
		newRecord.setGradeBO(gradeBLL.findByIdentifier(newRecord.getIdGrade()));
		newRecord.setSchoolBO(schoolBLL.findByIdentifier(newRecord.getIdSchool()));
		newRecord.setTimeBO(timeBLL.findByIdentifier(newRecord.getIdTime()));
		newRecord.setUserBO(userBLL.findByIdentifier(newRecord.getIdUser()));
		newRecord.setYearBO(yearBLL.findByIdentifier(newRecord.getIdYear()));
		Bzclassroom bzClassRoom = this.buildClassRoomHibernateEntity(newRecord);
		success = this.classRoomDAO.save(bzClassRoom); 
		this.putRecordInCache(bzClassRoom);
		return success;
	}

	@Override
	public boolean updateRecord(ClassRoomBO record) {
		boolean success = false;
		record.setUpdated(DateTime.now().toDate());
		Bzclassroom bzClassRoom = this.buildClassRoomHibernateEntity(record);
		success = this.classRoomDAO.save(bzClassRoom); 
		this.putRecordInCache(bzClassRoom);
		return success;
	}

	@Override
	public Set<ClassRoomBO> selectAndPutInCache() {
		Set<Bzclassroom> bzClassRoomSet = this.classRoomDAO.select();
		Set<ClassRoomBO> classRoomBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzClassRoomSet);
		if (classRoomBOSet != null) {
			this.cacheManager.putObjectsInCache(
					this.cacheManager.getCache(CACHE_KEY), classRoomBOSet);
		}
		return classRoomBOSet;
	}

	@Override
	public ClassRoomBO selectByIdentifierAndPutInCache(Integer identifier) {
		ClassRoomBO classRoomBO = null;
		Bzclassroom bzClassRoom = this.classRoomDAO.selectByIdentifier(identifier);
		if (bzClassRoom != null) {
			classRoomBO = new ClassRoomBO(bzClassRoom);
			this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), classRoomBO);
		}
		return classRoomBO;
	}

	@Override
	public Set<ClassRoomBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<ClassRoomBO> classRoomBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			classRoomBOSet = new HashSet<>();
			for (Object bzClassRoom : hibernateEntitySet) {
				if (bzClassRoom instanceof Bzclassroom) {
					classRoomBOSet.add(new ClassRoomBO(
							(Bzclassroom) bzClassRoom));
				}
			}
		}
		return classRoomBOSet;
	}

	public Set<ClassRoomBO> findBy(Integer classRoomId, int schoolId, String year, Integer grade, Integer time) {
		Set<ClassRoomBO> classRoomBOSet = null;
		Set<ClassRoomBO> cacheClassRoomBOSet = this.getObjectsFromCache();
		if (cacheClassRoomBOSet != null) {
			for (ClassRoomBO classRoomBO : cacheClassRoomBOSet) {
				if (classRoomBO.getSchoolBO().getId().equals(schoolId)
						&& (year == null || classRoomBO.getYearBO().getName().equals(year))
						&& (grade == null || classRoomBO.getGradeBO().getId().equals(grade))
						&& (time == null || classRoomBO.getTimeBO().getId().equals(time))
						&& (classRoomId == null || classRoomBO.getId().equals(classRoomId))) {
					if (classRoomBOSet == null) {
						classRoomBOSet = new HashSet<>();
					}
	
					classRoomBOSet.add(classRoomBO);
					LOGGER.info("ClassRoomBO object loaded: {}", classRoomBO);
				}
			}
		}
		return classRoomBOSet;
	}

	@SuppressWarnings("unchecked")
	private Set<ClassRoomBO> getObjectsFromCache() {
		return this.cacheManager.getObjectsFromCache(this.cacheManager
				.getCache(CACHE_KEY));
	}
	
	public Bzclassroom buildClassRoomHibernateEntity(ClassRoomBO classRoomBO) {
		Bzclassroom bzClassRoom = new Bzclassroom();
		bzClassRoom.setCode(classRoomBO.getCode());
		bzClassRoom.setName(classRoomBO.getName());
		bzClassRoom.setCreation(classRoomBO.getCreation());
		bzClassRoom.setUpdated(classRoomBO.getUpdated());
		bzClassRoom.setEnabled(classRoomBO.isEnabled());
		bzClassRoom.setBzgrade(gradeBLL.buildGradeHibernateEntity(classRoomBO.getGradeBO()));
		bzClassRoom.setBzschool(schoolBLL.buildSchoolHibernateEntity(classRoomBO.getSchoolBO()));
		bzClassRoom.setBztime(timeBLL.buildTimeHibernateEntity(classRoomBO.getTimeBO()));
		bzClassRoom.setBzuser(userBLL.buildUserHibernateEntity(classRoomBO.getUserBO()));
		bzClassRoom.setBzyear(yearBLL.buildYearHibernateEntity(classRoomBO.getYearBO()));
		return bzClassRoom;
	}
	
	private void putRecordInCache(Bzclassroom bzClassRoom) {
		ClassRoomBO classRoomBO = this.findByIdentifier(bzClassRoom.getId());
		this.cacheManager.putObjectInCache(
				this.cacheManager.getCache(CACHE_KEY), classRoomBO);
	}
}
