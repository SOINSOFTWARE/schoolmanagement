package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.schoolmanagement.hibernate.Cnaccess;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * Access data access object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public class AccessDAO extends AbstractDAO implements IDataAccesable<Cnaccess> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Cnaccess> select() {
		Set<Cnaccess> cnAccessSet = null;
		Chronometer chrono = this.startNewChronometer();
        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            cnAccessSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, AccessDAO.class.getName() + ", Select function");
        }
        
        return cnAccessSet;
	}

	@Override
	public Cnaccess selectByIdentifier(Integer identifier) {
		Cnaccess cnAccess = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER, identifier);
        	cnAccess = (Cnaccess) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, AccessDAO.class.getName() + ", selectByIdentifier function");
        }
		return cnAccess;
	}

	@Override
	public Cnaccess selectByCode(String code) {
		Cnaccess cnAccess = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByCode());
        	query.setParameter(COLUMN_CODE, code);
        	cnAccess = (Cnaccess) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, AccessDAO.class.getName() + ", selectByCode function");
        }
		return cnAccess;
	}

	@Override
	public void save(Cnaccess record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, AccessDAO.class.getName() + ", save function");
        }
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_ACCESS);
		return sql.toString();
	}
}