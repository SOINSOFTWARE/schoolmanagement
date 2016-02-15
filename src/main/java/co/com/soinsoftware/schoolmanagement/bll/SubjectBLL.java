package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.SubjectDAO;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.SubjectBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzsubject;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 28/01/2016
 */
@Service
public class SubjectBLL extends AbstractBLL implements
		IBusinessLogicLayer<SubjectBO, Bzsubject> {

	@Autowired
	private SubjectDAO subjectDAO;

	@Autowired
	private ClassRoomBLL classRoomBLL;

	@Override
	public Set<SubjectBO> findAll() {
		return this.isCacheEmpty(SUBJECT_KEY) ? this.selectAndPutInCache()
				: this.getObjectsFromCache();
	}

	@Override
	public Set<SubjectBO> findAll(final int idSchool) {
		return this.isCacheEmpty(SUBJECT_KEY) ? this.selectAndPutInCache()
				: this.getObjectsFromCache();
	}

	@Override
	public SubjectBO findByIdentifier(final Integer identifier) {
		SubjectBO subjectBO = null;
		if (!this.isCacheEmpty(SUBJECT_KEY)) {
			subjectBO = (SubjectBO) this.getObjectFromCache(SUBJECT_KEY,
					identifier);
		}
		if (subjectBO == null) {
			subjectBO = this.selectByIdentifierAndPutInCache(identifier);
		}
		if (subjectBO != null) {
			LOGGER.info("subject = {} was loaded successfully",
					subjectBO.toString());
		}
		return subjectBO;
	}

	@Override
	public SubjectBO findByCode(final int idSchool, final String code,
			final int identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<SubjectBO> findExcludingClass(final int idClassRoom) {
		final Set<SubjectBO> subjectSet = this.findAll();
		final ClassRoomBO classRoom = classRoomBLL
				.findByIdentifier(idClassRoom);
		final Set<SubjectBO> subjectExcludedSet = new HashSet<SubjectBO>();
		if (classRoom != null && subjectSet != null) {
			final Set<ClassBO> classSet = classRoom.getClassSet();
			if (!subjectSet.isEmpty()
					&& (classSet == null || (classSet != null && classSet
							.isEmpty()))) {
				for (SubjectBO subject : subjectSet) {
					if (subject.isEnabled()) {
						subjectExcludedSet.add(subject);
					}
				}
			} else if (!classSet.isEmpty() && !subjectSet.isEmpty()) {
				boolean found = false;
				for (SubjectBO subject : subjectSet) {
					if (subject.isEnabled()) {
						for (ClassBO classBO : classSet) {
							if (classBO.getSubject().equals(subject)) {
								found = true;
								break;
							}
						}
					}
					if (!found) {
						subjectExcludedSet.add(subject);
					}
					found = false;
				}
			}
		}
		return subjectExcludedSet;
	}

	@Override
	public SubjectBO saveRecord(final SubjectBO record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SubjectBO> selectAndPutInCache() {
		final Set<Bzsubject> bzSubjectSet = this.subjectDAO.select();
		final Set<SubjectBO> subjectBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzSubjectSet);
		if (subjectBOSet != null) {
			this.putObjectsInCache(SUBJECT_KEY, subjectBOSet);
		}
		return subjectBOSet;
	}

	@Override
	public SubjectBO selectByIdentifierAndPutInCache(final Integer identifier) {
		SubjectBO classBO = null;
		final Bzsubject bzSubject = this.subjectDAO
				.selectByIdentifier(identifier);
		if (bzSubject != null) {
			classBO = new SubjectBO(bzSubject);
			this.putObjectInCache(SUBJECT_KEY, classBO);
		}
		return classBO;
	}

	@Override
	public Set<SubjectBO> createEntityBOSetUsingHibernatEntities(
			final Set<?> hibernateEntitySet) {
		Set<SubjectBO> subjectBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			subjectBOSet = new HashSet<>();
			for (Object bzSubject : hibernateEntitySet) {
				if (bzSubject instanceof Bzsubject) {
					subjectBOSet.add(new SubjectBO((Bzsubject) bzSubject));
				}
			}
		}
		return subjectBOSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Set<SubjectBO> getObjectsFromCache() {
		return this.getObjectsFromCache(SUBJECT_KEY);
	}

	public Bzsubject buildHibernateEntity(final SubjectBO subjectBO) {
		final Bzsubject bzSubject = new Bzsubject();
		if (subjectBO != null) {
			bzSubject.setId(subjectBO.getId());
			bzSubject.setCode(subjectBO.getCode());
			bzSubject.setName(subjectBO.getName());
			bzSubject.setCreation(subjectBO.getCreation());
			bzSubject.setUpdated(subjectBO.getUpdated());
			bzSubject.setEnabled(subjectBO.isEnabled());
		}
		return bzSubject;
	}

	@Override
	public SubjectBO putObjectInCache(Bzsubject record) {
		// TODO Auto-generated method stub
		return null;
	}
}