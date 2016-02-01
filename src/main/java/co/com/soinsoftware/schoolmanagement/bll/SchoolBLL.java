/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.SchoolDAO;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzschool;

/**
 * School business logic layer
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
@Service
public class SchoolBLL extends AbstractBLL implements
		IBusinessLogicLayer<SchoolBO, Bzschool> {

	@Autowired
	private SchoolDAO schoolDAO;

	@Override
	public Set<SchoolBO> findAll() {
		return this.isCacheEmpty(SCHOOL_KEY) ? this.selectAndPutInCache()
				: this.getObjectsFromCache();
	}

	@Override
	public Set<SchoolBO> findAll(final int idSchool) {
		return null;
	}

	@Override
	public SchoolBO findByIdentifier(final Integer identifier) {
		SchoolBO schoolBO = null;
		if (!this.isCacheEmpty(SCHOOL_KEY)) {
			schoolBO = (SchoolBO) this.getObjectFromCache(SCHOOL_KEY,
					identifier);
		}
		if (schoolBO == null) {
			schoolBO = this.selectByIdentifierAndPutInCache(identifier);
		}
		if (schoolBO != null) {
			LOGGER.info("school = {} was loaded successfully",
					schoolBO.toString());
		}
		return schoolBO;
	}

	@Override
	public SchoolBO findByCode(final int idSchool, final String code,
			final int identifier) {
		return (SchoolBO) this.getObjectFromCache(SCHOOL_KEY, code);
	}

	@Override
	public SchoolBO saveRecord(final SchoolBO record) {
		return null;
	}

	@Override
	public Set<SchoolBO> selectAndPutInCache() {
		final Set<Bzschool> bzSchoolSet = this.schoolDAO.select();
		final Set<SchoolBO> schoolBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzSchoolSet);
		if (schoolBOSet != null) {
			this.putObjectsInCache(SCHOOL_KEY, schoolBOSet);
		}
		return schoolBOSet;
	}

	@Override
	public SchoolBO selectByIdentifierAndPutInCache(final Integer identifier) {
		SchoolBO schoolBO = null;
		final Bzschool bzSchool = this.schoolDAO.selectByIdentifier(identifier);
		if (bzSchool != null) {
			schoolBO = new SchoolBO(bzSchool);
			this.putObjectInCache(SCHOOL_KEY, schoolBO);
		}
		return schoolBO;
	}

	@Override
	public Set<SchoolBO> createEntityBOSetUsingHibernatEntities(
			final Set<?> hibernateEntitySet) {
		Set<SchoolBO> schoolBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			schoolBOSet = new HashSet<>();
			for (Object bzSchool : hibernateEntitySet) {
				if (bzSchool instanceof Bzschool) {
					schoolBOSet.add(new SchoolBO((Bzschool) bzSchool));
				}
			}
		}
		return schoolBOSet;
	}

	public Bzschool buildHibernateEntity(SchoolBO schoolBO) {
		Bzschool bzSchool = new Bzschool();
		bzSchool.setId(schoolBO.getId());
		bzSchool.setCode(schoolBO.getCode());
		bzSchool.setName(schoolBO.getName());
		bzSchool.setCreation(schoolBO.getCreation());
		bzSchool.setUpdated(schoolBO.getUpdated());
		bzSchool.setEnabled(schoolBO.isEnabled());
		return bzSchool;
	}

	@Override
	public SchoolBO putObjectInCache(Bzschool record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Set<SchoolBO> getObjectsFromCache() {
		// TODO Auto-generated method stub
		return null;
	}
}
