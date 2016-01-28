package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.ClassDAO;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclass;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 28/01/2016
 */
@Service
public class ClassBLL extends AbstractBLL implements
		IBusinessLogicLayer<ClassBO, Bzclass> {

	@Autowired
	private ClassDAO classDAO;

	@Autowired
	private ClassRoomBLL classRoomBLL;

	@Autowired
	private UserBLL userBLL;

	@Autowired
	private SubjectBLL subjectBLL;

	@Override
	public Set<ClassBO> findAll() {
		return this.isCacheEmpty(CLASS_KEY) ? this.selectAndPutInCache() : this
				.getObjectsFromCache();
	}

	@Override
	public ClassBO findByIdentifier(final Integer identifier) {
		ClassBO classBO = null;
		if (!this.isCacheEmpty(CLASS_KEY)) {
			classBO = (ClassBO) this.getObjectFromCache(CLASS_KEY, identifier);
		}
		if (classBO == null) {
			classBO = this.selectByIdentifierAndPutInCache(identifier);
		}
		if (classBO != null) {
			LOGGER.info("class = {} was loaded successfully",
					classBO.toString());
		}
		return classBO;
	}

	@Override
	public ClassBO findByCode(final String code) {
		return null;
	}

	@Override
	public ClassBO saveRecord(final ClassBO record) {
		return record.getId() == 0 ? this.insertRecord(record) : this
				.updateRecord(record);
	}

	@Override
	public ClassBO insertRecord(final ClassBO newRecord) {

		newRecord.setCreation(DateTime.now().toDate());
		newRecord.setUpdated(DateTime.now().toDate());
		newRecord.setEnabled(true);
		newRecord.setClassRoom(classRoomBLL.findByIdentifier(newRecord
				.getIdClassRoom()));
		newRecord.setSubject(subjectBLL.findByIdentifier(newRecord
				.getIdSubject()));
		newRecord
				.setTeacher(userBLL.findByIdentifier(newRecord.getIdTeacher()));
		Bzclass bzClass = this.buildHibernateEntity(newRecord);
		this.classDAO.save(bzClass);
		return this.putObjectInCache(bzClass);
	}

	@Override
	public ClassBO updateRecord(final ClassBO record) {
		record.setUpdated(DateTime.now().toDate());
		Bzclass bzClassRoom = this.buildHibernateEntity(record);
		this.classDAO.save(bzClassRoom);
		return this.putObjectInCache(bzClassRoom);
	}

	@Override
	public Set<ClassBO> selectAndPutInCache() {
		final Set<Bzclass> bzClassSet = this.classDAO.select();
		final Set<ClassBO> classBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzClassSet);
		if (classBOSet != null) {
			this.putObjectsInCache(CLASS_KEY, classBOSet);
		}
		return classBOSet;
	}

	@Override
	public ClassBO selectByIdentifierAndPutInCache(final Integer identifier) {
		ClassBO classBO = null;
		final Bzclass bzClass = this.classDAO.selectByIdentifier(identifier);
		if (bzClass != null) {
			classBO = new ClassBO(bzClass);
			this.putObjectInCache(CLASS_KEY, classBO);
		}
		return classBO;
	}

	@Override
	public Set<ClassBO> createEntityBOSetUsingHibernatEntities(
			final Set<?> hibernateEntitySet) {
		Set<ClassBO> classBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			classBOSet = new HashSet<>();
			for (Object bzClass : hibernateEntitySet) {
				if (bzClass instanceof Bzclass) {
					classBOSet.add(new ClassBO((Bzclass) bzClass));
				}
			}
		}
		return classBOSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Set<ClassBO> getObjectsFromCache() {
		return this.getObjectsFromCache(CLASS_KEY);
	}

	public Set<ClassBO> findByTeacher(final int idSchool, final int idUser) {
		Set<ClassBO> classBOSet = new HashSet<>();
		if (idSchool > 0 && idUser > 0) {
			final Set<ClassBO> cacheClassBOSet = this.getObjectsFromCache();
			if (cacheClassBOSet != null) {
				for (ClassBO classBO : cacheClassBOSet) {
					final UserBO teacher = classBO.getTeacher();
					if (teacher.getId() == idUser
							&& teacher.getSchool().getId() == idSchool) {
						classBOSet.add(classBO);
					}
				}
			}
		}
		return classBOSet;
	}

	public Bzclass buildHibernateEntity(final ClassBO classBO) {
		final Bzclass bzClass = new Bzclass();
		if (classBO != null) {
			bzClass.setId(classBO.getId());
			bzClass.setName(classBO.getName());
			bzClass.setCreation(classBO.getCreation());
			bzClass.setUpdated(classBO.getUpdated());
			bzClass.setEnabled(classBO.isEnabled());
			bzClass.setBzclassroom((classRoomBLL.buildHibernateEntity(classBO
					.getClassRoom())));
			bzClass.setBzsubject((subjectBLL.buildHibernateEntity(classBO
					.getSubject())));
			bzClass.setBzuser(userBLL.buildHibernateEntity(classBO.getTeacher()));
		}
		return bzClass;
	}

	public ClassBO putObjectInCache(final Bzclass bzClass) {
		final ClassBO classBO = this.findByIdentifier(bzClass.getId());
		this.putObjectInCache(CLASS_KEY, classBO);
		return classBO;
	}
}