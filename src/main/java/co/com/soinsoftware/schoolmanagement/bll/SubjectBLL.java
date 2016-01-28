package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.SubjectDAO;
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

	@Override
	public Set<SubjectBO> findAll() {
		return this.isCacheEmpty(SUBJECT_KEY) ? this.selectAndPutInCache()
				: this.getObjectsFromCache();
	}

	@Override
	public SubjectBO findByIdentifier(final Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubjectBO findByCode(final String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubjectBO saveRecord(final SubjectBO record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubjectBO insertRecord(final SubjectBO newRecord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubjectBO updateRecord(final SubjectBO record) {
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
		// TODO Auto-generated method stub
		return null;
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