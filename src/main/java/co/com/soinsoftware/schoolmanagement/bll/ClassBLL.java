package co.com.soinsoftware.schoolmanagement.bll;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.ClassDAO;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.NoteDefinitionBO;
import co.com.soinsoftware.schoolmanagement.entity.PeriodBO;
import co.com.soinsoftware.schoolmanagement.entity.SubjectBO;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;
import co.com.soinsoftware.schoolmanagement.entity.YearBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzclass;
import co.com.soinsoftware.schoolmanagement.hibernate.Bznotedefinition;

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
	public Set<ClassBO> findAll(final int idSchool) {
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
	public ClassBO findByCode(final int idSchool, final String code,
			final int identifier) {
		return null;
	}

	public Set<ClassBO> findBy(final int idSchool, final int idClassRoom,
			final int idTeacher) {
		final Set<ClassBO> classSet = new HashSet<>();
		final Set<ClassBO> cacheClassSet = this.findAll(idSchool);
		if (cacheClassSet != null && !cacheClassSet.isEmpty()) {
			for (final ClassBO classBO : cacheClassSet) {
				if ((classBO.getClassRoom().getId().equals(idClassRoom) && idTeacher == 0)
						|| (classBO.getTeacher().getId().equals(idTeacher) && idClassRoom == 0)) {
					classSet.add(classBO);
				}
			}
		}
		return classSet;
	}

	@Override
	public ClassBO saveRecord(final ClassBO record) {
		if (record.getClassRoom() == null) {
			record.setClassRoom(classRoomBLL.findByIdentifier(record
					.getIdClassRoom()));
		}
		if (record.getSubject() == null) {
			record.setSubject(subjectBLL.findByIdentifier(record.getIdSubject()));
		}
		if (record.getTeacher() == null) {
			record.setTeacher(userBLL.findByIdentifier(record.getIdTeacher()));
		}
		if (record.getNoteDefinitionSet() == null) {
			// TODO: Implement notedefinitionbll class and create a method to
			// find a set
			// using the class identifier
		}
		record.setUpdated(new Date());
		Bzclass bzClass = this.buildHibernateEntity(record);
		this.classDAO.save(bzClass);
		return this.putObjectInCache(bzClass);
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
			classBO = this.buildClassBO(bzClass);
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
					classBOSet.add(this.buildClassBO((Bzclass) bzClass));
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
							&& userBLL.isLinkedToSchool(teacher, idSchool)) {
						classBOSet.add(classBO);
					}
				}
			}
		}
		return classBOSet;
	}

	@Override
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

	@Override
	public ClassBO putObjectInCache(final Bzclass bzClass) {
		final Bzclass queryResult = classDAO
				.selectByIdentifier(bzClass.getId());
		final ClassBO classBO = this.buildClassBO(queryResult);
		this.putObjectInCache(CLASS_KEY, classBO);
		classRoomBLL.selectByIdentifierAndPutInCache(bzClass.getBzclassroom()
				.getId());
		return classBO;
	}

	@SuppressWarnings("unchecked")
	public ClassBO buildClassBO(final Bzclass bzClass) {
		final ClassRoomBO classRoom = classRoomBLL.buildClassRoomBO(
				bzClass.getBzclassroom(), false);
		final SubjectBO subject = new SubjectBO(bzClass.getBzsubject());
		final UserBO teacher = userBLL.buildUserBO(bzClass.getBzuser(), false);
		final Set<NoteDefinitionBO> noteDefinitionSet = this
				.buildNoteDefinitionSet(bzClass.getBznotedefinitions());
		return new ClassBO(bzClass, classRoom, subject, teacher,
				noteDefinitionSet);
	}

	public Set<ClassBO> buildClassSet(final Set<Bzclass> bzClassSet) {
		final Set<ClassBO> classSet = new HashSet<>();
		for (final Bzclass bzClass : bzClassSet) {
			final ClassBO classBO = this.buildClassBO(bzClass);
			classSet.add(classBO);
		}
		return classSet;
	}

	public Set<NoteDefinitionBO> buildNoteDefinitionSet(
			final Set<Bznotedefinition> bzNoteDefinitionSet) {
		final Set<NoteDefinitionBO> noteDefSet = new HashSet<>();
		for (final Bznotedefinition bzNoteDefinition : bzNoteDefinitionSet) {
			final YearBO year = new YearBO(bzNoteDefinition.getBzperiod()
					.getBzyear());
			final PeriodBO period = new PeriodBO(
					bzNoteDefinition.getBzperiod(), year);
			final NoteDefinitionBO noteDef = new NoteDefinitionBO(
					bzNoteDefinition, period);
			noteDefSet.add(noteDef);
		}
		return noteDefSet;
	}
}