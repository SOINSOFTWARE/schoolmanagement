/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.soinsoftware.schoolmanagement.hibernate.Cnusertype;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * User type data access object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
@Repository
public class UserTypeDAO extends AbstractDAO implements
		IDataAccesable<Cnusertype> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Cnusertype> select() {
		Set<Cnusertype> CnusertypeSet = null;
		Chronometer chrono = this.startNewChronometer();

		try {
			Query query = this.createQuery(this
					.getSelectStatementWithoutWhere());
			CnusertypeSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono,
					UserTypeDAO.class.getName() + ", Select function");
		}

		return CnusertypeSet;
	}

	@Override
	public Cnusertype selectByIdentifier(Integer identifier) {
		Cnusertype cnUserType = null;
		Chronometer chrono = this.startNewChronometer();
		try {
			Query query = this.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER, identifier);
			cnUserType = (Cnusertype) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			this.stopChronometerAndLogMessage(chrono,
					UserTypeDAO.class.getName()
							+ ", selectByIdentifier function");
		}
		return cnUserType;
	}

	@Override
	public Cnusertype selectByCode(String code) {
		Cnusertype cnUserType = null;
		Chronometer chrono = this.startNewChronometer();
		try {
			Query query = this.createQuery(this.getSelectStatementByCode());
			query.setParameter(COLUMN_CODE, code);
			cnUserType = (query.list().isEmpty()) ? null : (Cnusertype) query
					.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			this.stopChronometerAndLogMessage(chrono,
					UserTypeDAO.class.getName() + ", selectByCode function");
		}
		return cnUserType;
	}

	@Override
	public void save(Cnusertype record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono,
					UserTypeDAO.class.getName() + ", save function");
		}
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_USERTYPE);
		return sql.toString();
	}
}