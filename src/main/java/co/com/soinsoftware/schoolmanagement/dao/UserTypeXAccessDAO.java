package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.soinsoftware.schoolmanagement.hibernate.Cnusertypexaccess;
import co.com.soinsoftware.schoolmanagement.hibernate.CnusertypexaccessId;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 20/10/2015
 */
@Repository
public class UserTypeXAccessDAO extends AbstractDAO implements IDataAccesable<Cnusertypexaccess> {
	
	public static final String COLUMN_IDENTIFIER_USERTYPE = "idUserType";
	public static final String COLUMN_IDENTIFIER_ACCESS = "idAccess";

	@SuppressWarnings("unchecked")
	@Override
	public Set<Cnusertypexaccess> select() {
		Set<Cnusertypexaccess> cnusertypexaccessSet = null;
		Chronometer chrono = this.startNewChronometer();        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            cnusertypexaccessSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, UserTypeXAccessDAO.class.getName() + ", Select function");
        }
        return cnusertypexaccessSet;
	}

	@Override
	public Cnusertypexaccess selectByIdentifier(Integer identifier) {
		return null;
	}

	@Override
	public Cnusertypexaccess selectByCode(String code) {
		return null;
	}
	
	public Cnusertypexaccess selectByIdentifier(Integer idUserType, Integer idAccess) {
		Cnusertypexaccess cnusertypexaccess = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER_USERTYPE, idUserType);
        	query.setParameter(COLUMN_IDENTIFIER_ACCESS, idAccess);
        	cnusertypexaccess = (Cnusertypexaccess) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, UserTypeXAccessDAO.class.getName() + ", selectByIdentifier function");
        }
		return cnusertypexaccess;
	}

	@Override
	public void save(Cnusertypexaccess record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			CnusertypexaccessId cnusertypexaccessId = record.getId();
			Cnusertypexaccess cnusertypexaccess = this.selectByIdentifier(
					cnusertypexaccessId.getIdUserType(), cnusertypexaccessId.getIdAccess());
			boolean isNew = (cnusertypexaccess == null) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, SchoolXUserDAO.class.getName() + ", save function");
        }
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_USERTYPE_X_ACCESS);		
		return sql.toString();
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		StringBuilder sql = new StringBuilder(this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_IDENTIFIER_USERTYPE);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_USERTYPE);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_IDENTIFIER_ACCESS);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_ACCESS);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_ENABLED + " = 1");
		return sql.toString();		
	}
}
