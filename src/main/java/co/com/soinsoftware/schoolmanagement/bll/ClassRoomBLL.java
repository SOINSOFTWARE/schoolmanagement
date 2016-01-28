/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.ClassRoomDAO;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroom;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Service
public class ClassRoomBLL extends AbstractBLL implements
		IBusinessLogicLayer<ClassRoomBO, Bzclassroom> {

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

	@Override
	public Set<ClassRoomBO> findAll() {
		return this.isCacheEmpty(CLASSROOM_KEY) ? this.selectAndPutInCache()
				: this.getObjectsFromCache();
	}

	@Override
	public ClassRoomBO findByIdentifier(Integer identifier) {
		ClassRoomBO classRoomBO = null;
		if (!this.isCacheEmpty(CLASSROOM_KEY)) {
			classRoomBO = (ClassRoomBO) this.getObjectFromCache(CLASSROOM_KEY,
					identifier);
		}
		if (classRoomBO == null) {
			classRoomBO = this.selectByIdentifierAndPutInCache(identifier);
		}
		if (classRoomBO != null) {
			LOGGER.info("classRoom = {} was loaded successfully",
					classRoomBO.toString());
		}
		return classRoomBO;
	}

	@Override
	public ClassRoomBO findByCode(String code) {
		return (ClassRoomBO) this.getObjectFromCache(CLASSROOM_KEY, code);
	}

	@Override
	public ClassRoomBO saveRecord(final ClassRoomBO record) {
		return record.getId() == 0 ? this.insertRecord(record) : this
				.updateRecord(record);
	}

	@Override
	public ClassRoomBO insertRecord(final ClassRoomBO newRecord) {
		newRecord.setCreation(DateTime.now().toDate());
		newRecord.setUpdated(DateTime.now().toDate());
		newRecord.setEnabled(true);
		newRecord.setGrade(gradeBLL.findByIdentifier(newRecord.getIdGrade()));
		newRecord
				.setSchool(schoolBLL.findByIdentifier(newRecord.getIdSchool()));
		newRecord.setTime(timeBLL.findByIdentifier(newRecord.getIdTime()));
		newRecord.setTeacher(userBLL.findByIdentifier(newRecord.getIdUser()));
		newRecord.setYear(yearBLL.findByIdentifier(newRecord.getIdYear()));
		Bzclassroom bzClassRoom = this.buildHibernateEntity(newRecord);
		this.classRoomDAO.save(bzClassRoom);
		return this.putObjectInCache(bzClassRoom);
	}

	@Override
	public ClassRoomBO updateRecord(final ClassRoomBO record) {
		record.setUpdated(DateTime.now().toDate());
		Bzclassroom bzClassRoom = this.buildHibernateEntity(record);
		this.classRoomDAO.save(bzClassRoom);
		return this.putObjectInCache(bzClassRoom);
	}

	@Override
	public Set<ClassRoomBO> selectAndPutInCache() {
		Set<Bzclassroom> bzClassRoomSet = this.classRoomDAO.select();
		Set<ClassRoomBO> classRoomBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzClassRoomSet);
		if (classRoomBOSet != null) {
			this.putObjectsInCache(CLASSROOM_KEY, classRoomBOSet);
		}
		return classRoomBOSet;
	}

	@Override
	public ClassRoomBO selectByIdentifierAndPutInCache(Integer identifier) {
		ClassRoomBO classRoomBO = null;
		Bzclassroom bzClassRoom = this.classRoomDAO
				.selectByIdentifier(identifier);
		if (bzClassRoom != null) {
			classRoomBO = new ClassRoomBO(bzClassRoom);
			this.putObjectInCache(CLASSROOM_KEY, classRoomBO);
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

	public Set<ClassRoomBO> findBy(Integer classRoomId, int schoolId,
			String year, Integer grade, Integer time) {
		Set<ClassRoomBO> classRoomBOSet = new HashSet<>();
		Set<ClassRoomBO> cacheClassRoomBOSet = this.getObjectsFromCache();
		if (cacheClassRoomBOSet != null) {
			for (ClassRoomBO classRoomBO : cacheClassRoomBOSet) {
				if (classRoomBO.getSchool().getId().equals(schoolId)
						&& (year == null || classRoomBO.getYear().getName()
								.equals(year))
						&& (grade == null || classRoomBO.getGrade().getId()
								.equals(grade))
						&& (time == null || classRoomBO.getTime().getId()
								.equals(time))
						&& (classRoomId == null || classRoomBO.getId().equals(
								classRoomId))) {
					classRoomBOSet.add(classRoomBO);
					LOGGER.info("ClassRoomBO object loaded: {}", classRoomBO);
				}
			}
		}
		return classRoomBOSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Set<ClassRoomBO> getObjectsFromCache() {
		return this.getObjectsFromCache(CLASSROOM_KEY);
	}

	public Bzclassroom buildHibernateEntity(ClassRoomBO classRoomBO) {
		Bzclassroom bzClassRoom = new Bzclassroom();
		bzClassRoom.setCode(classRoomBO.getCode());
		bzClassRoom.setName(classRoomBO.getName());
		bzClassRoom.setCreation(classRoomBO.getCreation());
		bzClassRoom.setUpdated(classRoomBO.getUpdated());
		bzClassRoom.setEnabled(classRoomBO.isEnabled());
		bzClassRoom.setBzgrade(gradeBLL.buildHibernateEntity(classRoomBO
				.getGrade()));
		bzClassRoom.setBzschool(schoolBLL.buildHibernateEntity(classRoomBO
				.getSchool()));
		bzClassRoom.setBztime(timeBLL.buildHibernateEntity(classRoomBO
				.getTime()));
		bzClassRoom.setBzuser(userBLL.buildHibernateEntity(classRoomBO
				.getTeacher()));
		bzClassRoom.setBzyear(yearBLL.buildHibernateEntity(classRoomBO
				.getYear()));
		return bzClassRoom;
	}

	@Override
	public ClassRoomBO putObjectInCache(Bzclassroom bzClassRoom) {
		final ClassRoomBO classRoomBO = this.findByIdentifier(bzClassRoom
				.getId());
		this.putObjectInCache(CLASSROOM_KEY, classRoomBO);
		return classRoomBO;
	}
}
