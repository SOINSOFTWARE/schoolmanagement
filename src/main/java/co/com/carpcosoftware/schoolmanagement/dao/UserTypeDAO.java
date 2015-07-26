/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.carpcosoftware.schoolmanagement.hibernate.Cnusertype;
import co.com.carpcosoftware.schoolmanagement.util.Chronometer;

/**
 * User type data access object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
@Repository
public class UserTypeDAO extends AbstractDAO implements IDataAccesable<Cnusertype> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Cnusertype> select() {
		Set<Cnusertype> CnusertypeSet = null;
		Chronometer chrono = this.startNewChronometer();
        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            CnusertypeSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, UserTypeDAO.class.getName() + ", Select function");
        }
        
        return CnusertypeSet;
	}

	@Override
	public Cnusertype selectByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Cnusertype newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Cnusertype record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_USERTYPE);		
		return sql.toString();
	}

	@Override
	protected String getInsertStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getUpdateStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Cnusertype selectByCode(String code) {
		Cnusertype cnUserType = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByCode());
        	query.setParameter(COLUMN_CODE, code);
        	cnUserType = (Cnusertype) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, UserTypeDAO.class.getName() + ", selectByCode function");
        }
		return cnUserType;
	}
	
	private String getSelectStatementByCode() {
		StringBuilder sql = new StringBuilder(this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_CODE);
		sql.append(PARAMETER + COLUMN_CODE);
		return sql.toString();
	}
	
}
