/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzgrade;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
public class GradeDAO extends AbstractDAO implements IDataAccesable<Bzgrade> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.carpcosoftware.schoolmanagement.dao.IDataAccesable#select()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzgrade> select() {
		Set<Bzgrade> bzGradeSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzGradeSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, GradeDAO.class.getName()
					+ ", Select function");
		}
		return bzGradeSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.com.carpcosoftware.schoolmanagement.dao.IDataAccesable#selectByIdentifier
	 * (java.lang.Integer)
	 */
	@Override
	public Bzgrade selectByIdentifier(Integer identifier) {
		Bzgrade bzGrade = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER, identifier);
			bzGrade = (Bzgrade) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, GradeDAO.class.getName()
					+ ", selectByIdentifier function");
		}
		return bzGrade;
	}

	@Override
	public Bzgrade selectByCode(String code) {
		Bzgrade bzGrade = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this.getSelectStatementByCode());
			query.setParameter(COLUMN_CODE, code);
			bzGrade = (Bzgrade) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, GradeDAO.class.getName()
					+ ", selectByCode function");
		}
		return bzGrade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.com.carpcosoftware.schoolmanagement.dao.IDataAccesable#insert(java
	 * .lang.Object)
	 */
	@Override
	public void save(Bzgrade record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono, GradeDAO.class.getName()
					+ ", save function");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.carpcosoftware.schoolmanagement.dao.AbstractDAO#
	 * getSelectStatementWithoutWhere()
	 */
	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_GRADE);
		return sql.toString();
	}
}
