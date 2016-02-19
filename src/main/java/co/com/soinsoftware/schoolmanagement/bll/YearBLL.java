/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.bll;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.soinsoftware.schoolmanagement.dao.YearDAO;
import co.com.soinsoftware.schoolmanagement.entity.YearBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzyear;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
@Service
public class YearBLL extends AbstractBLL implements
		IBusinessLogicLayer<YearBO, Bzyear> {

	@Autowired
	private YearDAO yearDAO;

	@Override
	public Set<YearBO> findAll() {
		return this.isCacheEmpty(YEAR_KEY) ? this.selectAndPutInCache() : this
				.getObjectsFromCache();
	}

	@Override
	public Set<YearBO> findAll(final int idSchool) {
		return this.findAll();
	}

	@Override
	public YearBO findByIdentifier(final Integer identifier) {
		YearBO yearBO = null;
		if (!this.isCacheEmpty(YEAR_KEY)) {
			yearBO = (YearBO) this.getObjectFromCache(YEAR_KEY, identifier);
		}
		if (yearBO == null) {
			yearBO = this.selectByIdentifierAndPutInCache(identifier);
		}
		if (yearBO != null) {
			LOGGER.info("year = {} was loaded successfully", yearBO.toString());
		}
		return yearBO;
	}

	@Override
	public YearBO findByCode(final int idSchool, final String code,
			final int identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public YearBO saveRecord(final YearBO record) {
		return null;
	}

	@Override
	public Set<YearBO> selectAndPutInCache() {
		final Set<Bzyear> bzYearSet = this.yearDAO.select();
		final Set<YearBO> YearBOSet = this
				.createEntityBOSetUsingHibernatEntities(bzYearSet);
		if (YearBOSet != null) {
			this.putObjectsInCache(YEAR_KEY, YearBOSet);
		}
		return YearBOSet;
	}

	@Override
	public YearBO selectByIdentifierAndPutInCache(final Integer identifier) {
		YearBO yearBO = null;
		final Bzyear bzYear = this.yearDAO.selectByIdentifier(identifier);
		if (bzYear != null) {
			yearBO = new YearBO(bzYear);
			this.putObjectInCache(YEAR_KEY, yearBO);
		}
		return yearBO;
	}

	@Override
	public Set<YearBO> createEntityBOSetUsingHibernatEntities(
			final Set<?> hibernateEntitySet) {
		Set<YearBO> yearBOSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			yearBOSet = new HashSet<>();
			for (Object bzYear : hibernateEntitySet) {
				if (bzYear instanceof Bzyear) {
					yearBOSet.add(new YearBO((Bzyear) bzYear));
				}
			}
		}
		return yearBOSet;
	}

	@SuppressWarnings("unchecked")
	protected Set<YearBO> getObjectsFromCache() {
		return this.getObjectsFromCache(YEAR_KEY);
	}

	public YearBO findCurrentYear() {
		YearBO currentYear = null;
		final Set<YearBO> yearBOSet = this.findAll();
		for (YearBO year : yearBOSet) {
			if (year.isEnabled()) {
				currentYear = year;
				LOGGER.info("Current year loaded: {}", currentYear.toString());
				break;
			}
		}
		return currentYear;
	}
	
	public int findLastYear() {
		final YearBO year = findCurrentYear();
		return (Integer.valueOf(year.getName()) - 1);
	}

	@Override
	public Bzyear buildHibernateEntity(final YearBO yearBO) {
		Bzyear bzYear = new Bzyear();
		if (yearBO != null) {
			bzYear.setId(yearBO.getId());
			bzYear.setName(yearBO.getName());
			bzYear.setCreation(yearBO.getCreation());
			bzYear.setUpdated(yearBO.getUpdated());
			bzYear.setEnabled(yearBO.isEnabled());
		}
		return bzYear;
	}

	@Override
	public YearBO putObjectInCache(Bzyear record) {
		// TODO Auto-generated method stub
		return null;
	}

}
