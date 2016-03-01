/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.bll;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.ClassRoomDAO;
import co.com.soinsoftware.schoolmanagement.dao.ClassRoomXUserDAO;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.GradeBO;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.entity.TimeBO;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;
import co.com.soinsoftware.schoolmanagement.entity.YearBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroom;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroomxuser;
import co.com.soinsoftware.schoolmanagement.hibernate.BzclassroomxuserId;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzuser;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Service
public class ClassRoomBLL extends AbstractBLL implements
		IBusinessLogicLayer<ClassRoomBO, Bzclassroom> {

	@Autowired
	private ClassBLL classBLL;

	@Autowired
	private ClassRoomDAO classRoomDAO;

	@Autowired
	private ClassRoomXUserDAO classRoomXUserDAO;

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
	public Set<ClassRoomBO> findAll(final int idSchool) {
		final Set<ClassRoomBO> classRoomSet = this.isCacheEmpty(CLASSROOM_KEY) ? this
				.selectAndPutInCache() : this.getObjectsFromCache();
		final Set<ClassRoomBO> activeClassRoomSet = new HashSet<>();
		if (classRoomSet != null) {
			for (ClassRoomBO classRoom : classRoomSet) {
				final SchoolBO school = classRoom.getSchool();
				if (classRoom.isEnabled() && school.getId().equals(idSchool)) {
					activeClassRoomSet.add(classRoom);
				}
			}
		}
		return activeClassRoomSet;
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
	public ClassRoomBO findByCode(final int idSchool, final String code,
			final int identifier) {
		final YearBO year = yearBLL.findCurrentYear();
		ClassRoomBO classRoom = (ClassRoomBO) this.getObjectFromCache(
				CLASSROOM_KEY, code);
		if (classRoom == null) {
			final Bzclassroom bzClassRoom = classRoomDAO.selectByCode(code);
			if (bzClassRoom != null) {
				classRoom = this.buildClassRoomBO(bzClassRoom, true);
			}
		}
		return (classRoom != null
				&& classRoom.getSchool().getId().equals(idSchool)
				&& !classRoom.getId().equals(identifier) 
				&& classRoom.getYear().equals(year)) ? classRoom : null;
	}

	@Override
	public ClassRoomBO saveRecord(final ClassRoomBO record) {
		if (record.getGrade() == null) {
			record.setGrade(gradeBLL.findByIdentifier(record.getIdGrade()));
		}
		if (record.getSchool() == null) {
			record.setSchool(schoolBLL.findByIdentifier(record.getIdSchool()));
		}
		if (record.getTime() == null) {
			record.setTime(timeBLL.findByIdentifier(record.getIdTime()));
		}
		if (record.getTeacher() == null) {
			record.setTeacher(userBLL.findByIdentifier(record.getIdUser()));
		}
		if (record.getYear() == null) {
			record.setYear(yearBLL.findByIdentifier(record.getIdYear()));
		}
		record.setUpdated(new Date());
		final Bzclassroom bzClassRoom = this.buildHibernateEntity(record);
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
			classRoomBO = this.buildClassRoomBO(bzClassRoom, true);
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
					classRoomBOSet.add(this.buildClassRoomBO(
							(Bzclassroom) bzClassRoom, true));
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
				final SchoolBO schoolBO = classRoomBO.getSchool();
				final YearBO yearBO = classRoomBO.getYear();
				final GradeBO gradeBO = classRoomBO.getGrade();
				final TimeBO timeBO = classRoomBO.getTime();
				if (schoolBO.getId().equals(schoolId)
						&& (year == null || yearBO.getName().equals(year))
						&& (grade == null || gradeBO.getId().equals(grade))
						&& (time == null || timeBO.getId().equals(time))
						&& (classRoomId == null || classRoomBO.getId().equals(
								classRoomId)) && classRoomBO.isEnabled()) {
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

	public Bzclassroom buildHibernateEntity(final ClassRoomBO classRoomBO) {
		Bzclassroom bzClassRoom = new Bzclassroom();
		if (classRoomBO != null) {
			bzClassRoom.setId(classRoomBO.getId());
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
		}
		return bzClassRoom;
	}

	@Override
	public ClassRoomBO putObjectInCache(Bzclassroom bzClassRoom) {
		final Bzclassroom queryResult = classRoomDAO
				.selectByIdentifier(bzClassRoom.getId());
		final ClassRoomBO classRoomBO = this
				.buildClassRoomBO(queryResult, true);
		this.putObjectInCache(CLASSROOM_KEY, classRoomBO);
		return classRoomBO;
	}

	@SuppressWarnings("unchecked")
	public ClassRoomBO buildClassRoomBO(final Bzclassroom bzClassRoom,
			final boolean includeClassBO) {
		final SchoolBO school = new SchoolBO(bzClassRoom.getBzschool());
		final GradeBO grade = new GradeBO(bzClassRoom.getBzgrade());
		final TimeBO time = new TimeBO(bzClassRoom.getBztime());
		final YearBO year = new YearBO(bzClassRoom.getBzyear());
		final UserBO teacher = userBLL.buildUserBO(bzClassRoom.getBzuser(),
				false);
		Set<ClassBO> classSet = null;
		if (includeClassBO) {
			classSet = classBLL.buildClassSet(bzClassRoom.getBzclasses());
		}
		final Set<UserBO> studentSet = userBLL.buildUserSet(bzClassRoom
				.getBzclassroomxusers());
		return new ClassRoomBO(bzClassRoom, school, grade, time, teacher, year,
				classSet, studentSet);
	}

	public ClassRoomBO saveClassRoomXStudent(final ClassRoomBO classRoom) {
		ClassRoomBO savedClassRoom = null;
		if (classRoom != null && classRoom.getId() != null
				&& classRoom.getId() > 0 && classRoom.getStudentSet() != null) {
			final ClassRoomBO cachedClassRoom = this.findByIdentifier(classRoom
					.getId());
			for (final UserBO student : classRoom.getStudentSet()) {
				if (student.getId() != null && student.getId() > 0) {
					final UserBO cachedStudent = userBLL
							.findByIdentifier(student.getId());
					cachedClassRoom.addStudentToStudentSet(cachedStudent);
					final Bzclassroomxuser bzClassRoomxUser = this
							.buildClassRoomXUserHibernateEntity(
									cachedClassRoom, cachedStudent);
					classRoomXUserDAO.save(bzClassRoomxUser);
					final Bzclassroom bzClassRoom = this
							.buildHibernateEntity(cachedClassRoom);
					this.putObjectInCache(bzClassRoom);
					savedClassRoom = classRoom;
				}
			}
		}
		return savedClassRoom;
	}

	private Bzclassroomxuser buildClassRoomXUserHibernateEntity(
			final ClassRoomBO classRoom, final UserBO student) {
		final BzclassroomxuserId bzClassRoomxUserId = new BzclassroomxuserId(
				classRoom.getId(), student.getId());
		final Bzclassroom bzClassRoom = this.buildHibernateEntity(classRoom);
		final Bzuser bzUser = userBLL.buildHibernateEntity(student);
		final Bzclassroomxuser bzClassRoomxUser = new Bzclassroomxuser(
				bzClassRoomxUserId, bzClassRoom, bzUser, new Date(),
				new Date(), true);
		return bzClassRoomxUser;
	}
}