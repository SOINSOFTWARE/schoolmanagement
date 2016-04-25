/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.PeriodDAO;
import co.com.soinsoftware.schoolmanagement.entity.PeriodBO;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.entity.YearBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzperiod;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 21/04/2016
 */
@Service
public class PeriodBLL extends AbstractBLL implements
		IBusinessLogicLayer<PeriodBO, Bzperiod> {

	@Autowired
	private PeriodDAO dao;

	@Autowired
	private SchoolBLL schoolBLL;

	@Override
	public Set<PeriodBO> findAll() {
		return this.isCacheEmpty(PERIOD_KEY) ? this.selectAndPutInCache()
				: this.getObjectsFromCache();
	}

	@Override
	public Set<PeriodBO> findAll(int idSchool) {
		final SchoolBO schoolBO = this.schoolBLL.findByIdentifier(idSchool);
		final Set<PeriodBO> periodSet = this.findAll();
		final Set<PeriodBO> periodBySchoolSet = new HashSet<>();
		if (periodSet != null) {
			for (final PeriodBO period : periodSet) {
				final int numPeriod = Integer.parseInt(period.getCode());
				if (numPeriod <= schoolBO.getPeriod().intValue()) {
					periodBySchoolSet.add(period);
				}
			}
		}
		return periodBySchoolSet;
	}

	@Override
	public PeriodBO findByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PeriodBO findByCode(int idSchool, String code, int identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PeriodBO saveRecord(PeriodBO record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PeriodBO> selectAndPutInCache() {
		final Set<Bzperiod> bzPeriodSet = this.dao.select();
		final Set<PeriodBO> periodBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzPeriodSet);
		if (periodBOSet != null) {
			this.putObjectsInCache(PERIOD_KEY, periodBOSet);
		}
		return periodBOSet;
	}

	@Override
	public PeriodBO selectByIdentifierAndPutInCache(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PeriodBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<PeriodBO> periodBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			periodBOSet = new HashSet<>();
			for (Object bzPeriod : hibernateEntitySet) {
				if (bzPeriod instanceof Bzperiod) {
					final YearBO year = new YearBO(
							((Bzperiod) bzPeriod).getBzyear());
					periodBOSet.add(new PeriodBO((Bzperiod) bzPeriod, year));
				}
			}
		}
		return periodBOSet;
	}

	@Override
	public PeriodBO putObjectInCache(Bzperiod record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bzperiod buildHibernateEntity(PeriodBO record) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Set<PeriodBO> getObjectsFromCache() {
		return this.getObjectsFromCache(PERIOD_KEY);
	}
}
