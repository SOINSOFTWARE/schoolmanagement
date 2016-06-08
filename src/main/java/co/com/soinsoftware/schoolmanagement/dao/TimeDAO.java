/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bztime;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 27/08/2015
 */
public class TimeDAO extends AbstractDAO implements IDataAccesable<Bztime> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bztime> select() {
		Set<Bztime> bzTimeSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzTimeSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, TimeDAO.class.getName()
					+ ", Select function");
		}

		return bzTimeSet;
	}

	@Override
	public Bztime selectByIdentifier(Integer identifier) {
		Bztime bzTime = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER, identifier);
			bzTime = (Bztime) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, TimeDAO.class.getName()
					+ ", selectByIdentifier function");
		}
		return bzTime;
	}

	@Override
	public Bztime selectByCode(String code) {
		Bztime bzTime = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this.getSelectStatementByCode());
			query.setParameter(COLUMN_CODE, code);
			bzTime = (Bztime) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, TimeDAO.class.getName()
					+ ", selectByCode function");
		}
		return bzTime;
	}

	@Override
	public void save(Bztime record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono, TimeDAO.class.getName()
					+ ", save function");
		}
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_TIME);
		return sql.toString();
	}
}