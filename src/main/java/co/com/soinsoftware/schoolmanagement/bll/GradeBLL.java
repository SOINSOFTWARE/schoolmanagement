/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import co.com.soinsoftware.schoolmanagement.dao.GradeDAO;
import co.com.soinsoftware.schoolmanagement.entity.GradeBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzgrade;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
public class GradeBLL extends AbstractBLL implements
		IBusinessLogicLayer<GradeBO, Bzgrade> {

	private final GradeDAO dao;
	
	private static GradeBLL instance;
	
	private GradeBLL() {
		super();
		this.dao = new GradeDAO();
	}
	
	public static GradeBLL getInstance() {
		if (instance == null) {
			instance = new GradeBLL();
		}
		return instance;
	}

	@Override
	public Set<GradeBO> findAll() {
		return this.isCacheEmpty(GRADE_KEY) ? this.selectAndPutInCache() : this
				.getObjectsFromCache();
	}

	@Override
	public Set<GradeBO> findAll(final int idSchool) {
		return this.isCacheEmpty(GRADE_KEY) ? this.selectAndPutInCache() : this
				.getObjectsFromCache();
	}

	@Override
	public GradeBO findByIdentifier(final Integer identifier) {
		GradeBO gradeBO = null;
		if (!this.isCacheEmpty(GRADE_KEY)) {
			gradeBO = (GradeBO) this.getObjectFromCache(GRADE_KEY, identifier);
		}
		if (gradeBO == null) {
			gradeBO = this.selectByIdentifierAndPutInCache(identifier);
		}
		if (gradeBO != null) {
			LOGGER.info("grade = {} was loaded successfully",
					gradeBO.toString());
		}
		return gradeBO;
	}

	@Override
	public GradeBO findByCode(final int idSchool, final String code,
			final int identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GradeBO saveRecord(final GradeBO record) {
		return null;
	}

	@Override
	public Set<GradeBO> selectAndPutInCache() {
		Set<Bzgrade> bzGradeSet = this.dao.select();
		Set<GradeBO> gradeBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzGradeSet);
		if (gradeBOSet != null) {
			this.putObjectsInCache(GRADE_KEY, gradeBOSet);
		}
		return gradeBOSet;
	}

	@Override
	public GradeBO selectByIdentifierAndPutInCache(final Integer identifier) {
		GradeBO gradeBO = null;
		final Bzgrade bzGrade = this.dao.selectByIdentifier(identifier);
		if (bzGrade != null) {
			gradeBO = new GradeBO(bzGrade);
			this.putObjectInCache(GRADE_KEY, gradeBO);
		}
		return gradeBO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.carpcosoftware.schoolmanagement.bll.IBusinessLogicLayer#
	 * createEntityBOSetUsingHibernatEntities(java.util.Set)
	 */
	@Override
	public Set<GradeBO> createEntityBOSetUsingHibernatEntities(
			final Set<?> hibernateEntitySet) {
		Set<GradeBO> gradeBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			gradeBOSet = new HashSet<>();
			for (Object bzGrade : hibernateEntitySet) {
				if (bzGrade instanceof Bzgrade) {
					gradeBOSet.add(new GradeBO((Bzgrade) bzGrade));
				}
			}
		}
		return gradeBOSet;
	}

	@SuppressWarnings("unchecked")
	protected Set<GradeBO> getObjectsFromCache() {
		return this.getObjectsFromCache(GRADE_KEY);
	}

	@Override
	public Bzgrade buildHibernateEntity(final GradeBO gradeBO) {
		Bzgrade bzGrade = new Bzgrade();
		bzGrade.setId(gradeBO.getId());
		bzGrade.setCode(gradeBO.getCode());
		bzGrade.setName(gradeBO.getName());
		bzGrade.setCreation(gradeBO.getCreation());
		bzGrade.setUpdated(gradeBO.getUpdated());
		bzGrade.setEnabled(gradeBO.isEnabled());
		return bzGrade;
	}

	@Override
	public GradeBO putObjectInCache(Bzgrade record) {
		// TODO Auto-generated method stub
		return null;
	}
}