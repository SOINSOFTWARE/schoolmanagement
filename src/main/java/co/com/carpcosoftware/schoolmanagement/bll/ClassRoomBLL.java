/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

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
	protected CacheManager cacheManager;

	@Override
	public Set<ClassRoomBO> findAll() {
		return this.cacheManager.isCacheEmpty(CACHE_KEY) ? this
				.selectAndPutInCache() : this.getObjectsFromCache();
	}

	@Override
	public ClassRoomBO findByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassRoomBO findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertRecord(ClassRoomBO newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(ClassRoomBO record) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
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

	public Set<ClassRoomBO> findBy(Integer classRoomId, int schoolId, String year, Integer grade) {
		Set<ClassRoomBO> classRoomBOSet = null;
		Set<ClassRoomBO> cacheClassRoomBOSet = this.getObjectsFromCache();
		if (cacheClassRoomBOSet != null) {
			for (ClassRoomBO classRoomBO : cacheClassRoomBOSet) {
				if (classRoomBO.getSchoolBO().getId().equals(schoolId)
						&& (year == null || classRoomBO.getYearBO().getName().equals(year))
						&& (grade == null || classRoomBO.getGradeBO().getId().equals(grade))
						&& (classRoomId == null || classRoomBO.getId().equals(classRoomId))) {
					if (classRoomBOSet == null) {
						classRoomBOSet = new HashSet<>();
					}
	
					classRoomBOSet.add(classRoomBO);
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
}
