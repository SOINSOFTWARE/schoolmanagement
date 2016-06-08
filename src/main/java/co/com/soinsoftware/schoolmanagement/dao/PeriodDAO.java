package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzperiod;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 20/10/2015
 */
public class PeriodDAO extends AbstractDAO implements IDataAccesable<Bzperiod> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzperiod> select() {
		Set<Bzperiod> bzPeriodSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzPeriodSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, PeriodDAO.class.getName()
					+ ", Select function");
		}
		return bzPeriodSet;
	}

	@Override
	public Bzperiod selectByIdentifier(Integer identifier) {
		Bzperiod bzPeriod = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER, identifier);
			bzPeriod = (Bzperiod) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, PeriodDAO.class.getName()
					+ ", selectByIdentifier function");
		}
		return bzPeriod;
	}

	@Override
	public Bzperiod selectByCode(String code) {
		return null;
	}

	@Override
	public void save(Bzperiod record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono, PeriodDAO.class.getName()
					+ ", save function");
		}
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_PERIOD);
		return sql.toString();
	}

}
