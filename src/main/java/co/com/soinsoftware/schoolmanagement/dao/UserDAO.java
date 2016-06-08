package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzuser;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * User data access object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public class UserDAO extends AbstractDAO implements IDataAccesable<Bzuser> {

	private static final String COLUMN_DOCUMENT_NUMBER = "documentNumber";

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzuser> select() {
		Set<Bzuser> bzUserSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzUserSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, UserDAO.class.getName()
					+ ", Select function");
		}
		return bzUserSet;
	}

	@Override
	public Bzuser selectByIdentifier(Integer identifier) {
		Bzuser bzUser = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER, identifier);
			bzUser = (query.list().isEmpty()) ? null : (Bzuser) query.list()
					.get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, UserDAO.class.getName()
					+ ", selectByIdentifier function");
		}
		return bzUser;
	}

	@Override
	public Bzuser selectByCode(String code) {
		return null;
	}

	public Bzuser selectByDocumentNumber(String documentNumber) {
		Bzuser bzUser = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByDocumentNumber());
			query.setParameter(COLUMN_DOCUMENT_NUMBER, documentNumber);
			bzUser = (query.list().isEmpty()) ? null : (Bzuser) query.list()
					.get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, UserDAO.class.getName()
					+ ", selectByDocumentNumber function");
		}
		return bzUser;
	}

	@Override
	public void save(Bzuser record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono, UserDAO.class.getName()
					+ ", save function");
		}
	}

	@SuppressWarnings("unchecked")
	public Set<Bzuser> selectByGuardian(final Integer idUser) {
		Set<Bzuser> bzUserSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByGuardian());
			query.setParameter(COLUMN_GUARDIAN1, idUser);
			query.setParameter(COLUMN_GUARDIAN2, idUser);
			bzUserSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono, UserDAO.class.getName()
					+ ", Select function");
		}
		return bzUserSet;
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_USER);
		return sql.toString();
	}

	private String getSelectStatementByDocumentNumber() {
		StringBuilder sql = new StringBuilder(
				this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_DOCUMENT_NUMBER);
		sql.append(PARAMETER + COLUMN_DOCUMENT_NUMBER);
		return sql.toString();
	}

	private String getSelectStatementByGuardian() {
		final StringBuilder sql = new StringBuilder(
				this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_GUARDIAN1);
		sql.append(PARAMETER + COLUMN_GUARDIAN1);
		sql.append(STATEMENT_OR);
		sql.append(COLUMN_GUARDIAN2);
		sql.append(PARAMETER + COLUMN_GUARDIAN2);
		return sql.toString();
	}
}