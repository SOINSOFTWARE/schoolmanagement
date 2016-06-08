/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzyear;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
public class YearDAO extends AbstractDAO implements IDataAccesable<Bzyear> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzyear> select() {
		Set<Bzyear> bzYearSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzYearSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, YearDAO.class.getName()
					+ ", Select function");
		}

		return bzYearSet;
	}

	@Override
	public Bzyear selectByIdentifier(Integer identifier) {
		Bzyear bzYear = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER, identifier);
			bzYear = (Bzyear) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, YearDAO.class.getName()
					+ ", selectByIdentifier function");
		}
		return bzYear;
	}

	@Override
	public Bzyear selectByCode(String code) {
		Bzyear bzYear = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this.getSelectStatementByCode());
			query.setParameter(COLUMN_CODE, code);
			bzYear = (Bzyear) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, YearDAO.class.getName()
					+ ", selectByCode function");
		}
		return bzYear;
	}

	@Override
	public void save(Bzyear record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono, YearDAO.class.getName()
					+ ", save function");
		}
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_YEAR);
		return sql.toString();
	}
}