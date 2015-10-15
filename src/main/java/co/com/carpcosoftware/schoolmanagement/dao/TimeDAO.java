/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bztime;
import co.com.carpcosoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 27/08/2015
 */
@Repository
public class TimeDAO extends AbstractDAO implements IDataAccesable<Bztime> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bztime> select() {
		Set<Bztime> bzTimeSet = null;
		Chronometer chrono = this.startNewChronometer();
        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzTimeSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, TimeDAO.class.getName() + ", Select function");
        }
        
        return bzTimeSet;
	}

	@Override
	public Bztime selectByIdentifier(Integer identifier) {
		Bztime bzTime = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER, identifier);
        	bzTime = (Bztime) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, TimeDAO.class.getName() + ", selectByIdentifier function");
        }
		return bzTime;
	}
	
	@Override
	public Bztime selectByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Bztime record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_TIME);		
		return sql.toString();
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		StringBuilder sql = new StringBuilder(this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_IDENTIFIER);
		sql.append(PARAMETER + COLUMN_IDENTIFIER);
		return sql.toString();
	}

	@Override
	protected String getSelectStatementByCode() {
		// TODO Auto-generated method stub
		return null;
	}
}