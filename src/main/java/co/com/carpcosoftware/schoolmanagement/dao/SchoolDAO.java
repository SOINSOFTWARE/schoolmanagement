/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzschool;
import co.com.carpcosoftware.schoolmanagement.util.Chronometer;

/**
 * School data access object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
@Repository
public class SchoolDAO extends AbstractDAO implements IDataAccesable<Bzschool> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzschool> select() {
		Set<Bzschool> bzSchoolSet = null;
		Chronometer chrono = this.startNewChronometer();
		
        try {
        	Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzSchoolSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, SchoolDAO.class.getName() + ", Select function");
        }
		return bzSchoolSet;
	}

	@Override
	public Bzschool selectByIdentifier(Integer identifier) {
		Bzschool bzSchool = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER, identifier);
            bzSchool = (Bzschool) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, SchoolDAO.class.getName() + ", selectByIdentifier function");
        }
		return bzSchool;
	}

	@Override
	public Bzschool selectByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Bzschool record) {
		return false;
	}
	
	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_SCHOOL);		
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
