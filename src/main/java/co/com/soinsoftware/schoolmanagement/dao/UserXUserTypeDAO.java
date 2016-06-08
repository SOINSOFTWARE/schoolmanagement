package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzuserxusertype;
import co.com.soinsoftware.schoolmanagement.hibernate.BzuserxusertypeId;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 20/10/2015
 */
public class UserXUserTypeDAO extends AbstractDAO implements
		IDataAccesable<Bzuserxusertype> {

	public static final String COLUMN_IDENTIFIER_USER = "idUser";
	public static final String COLUMN_IDENTIFIER_USERTYPE = "idUserType";

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzuserxusertype> select() {
		Set<Bzuserxusertype> bzuserxusertypeSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzuserxusertypeSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono,
					UserXUserTypeDAO.class.getName() + ", Select function");
		}
		return bzuserxusertypeSet;
	}

	@Override
	public Bzuserxusertype selectByIdentifier(Integer identifier) {
		return null;
	}

	@Override
	public Bzuserxusertype selectByCode(String code) {
		return null;
	}

	public Bzuserxusertype selectByIdentifier(Integer idUser, Integer idUserType) {
		Bzuserxusertype bzuserxusertype = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER_USER, idUser);
			query.setParameter(COLUMN_IDENTIFIER_USERTYPE, idUserType);
			bzuserxusertype = (query.list().isEmpty()) ? null
					: (Bzuserxusertype) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono,
					UserXUserTypeDAO.class.getName()
							+ ", selectByIdentifier function");
		}
		return bzuserxusertype;
	}

	@Override
	public void save(Bzuserxusertype record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			BzuserxusertypeId bzuserxusertypeId = record.getId();
			Bzuserxusertype bzuserxusertype = this.selectByIdentifier(
					bzuserxusertypeId.getIdUser(),
					bzuserxusertypeId.getIdUserType());
			boolean isNew = (bzuserxusertype == null) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono,
					UserXUserTypeDAO.class.getName() + ", save function");
		}

	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_USER_X_USERTYPE);
		return sql.toString();
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		StringBuilder sql = new StringBuilder(
				this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_IDENTIFIER_USER);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_USER);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_IDENTIFIER_USERTYPE);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_USERTYPE);
		return sql.toString();
	}
}
