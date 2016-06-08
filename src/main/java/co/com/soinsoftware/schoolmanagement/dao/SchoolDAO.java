/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzschool;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * School data access object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
public class SchoolDAO extends AbstractDAO implements IDataAccesable<Bzschool> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzschool> select() {
		Set<Bzschool> bzSchoolSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzSchoolSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, SchoolDAO.class.getName()
					+ ", Select function");
		}
		return bzSchoolSet;
	}

	@Override
	public Bzschool selectByIdentifier(Integer identifier) {
		Bzschool bzSchool = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER, identifier);
			bzSchool = (query.list().isEmpty()) ? null : (Bzschool) query
					.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, SchoolDAO.class.getName()
					+ ", selectByIdentifier function");
		}
		return bzSchool;
	}

	@Override
	public Bzschool selectByCode(String code) {
		Bzschool bzSchool = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this.getSelectStatementByCode());
			query.setParameter(COLUMN_CODE, code);
			bzSchool = (query.list().isEmpty()) ? null : (Bzschool) query
					.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, SchoolDAO.class.getName()
					+ ", selectByCode function");
		}
		return bzSchool;
	}

	@Override
	public void save(Bzschool record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono, SchoolDAO.class.getName()
					+ ", save function");
		}
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_SCHOOL);
		return sql.toString();
	}
}