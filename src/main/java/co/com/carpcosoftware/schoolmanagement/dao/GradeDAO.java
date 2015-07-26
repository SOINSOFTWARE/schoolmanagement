/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzgrade;
import co.com.carpcosoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
@Repository
public class GradeDAO extends AbstractDAO implements IDataAccesable<Bzgrade> {

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.dao.IDataAccesable#select()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzgrade> select() {
		Set<Bzgrade> bzGradeSet = null;
		Chronometer chrono = this.startNewChronometer();
        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzGradeSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, GradeDAO.class.getName() + ", Select function");
        }
        
        return bzGradeSet;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.dao.IDataAccesable#selectByIdentifier(java.lang.Integer)
	 */
	@Override
	public Bzgrade selectByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.dao.IDataAccesable#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(Bzgrade newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.dao.IDataAccesable#update(java.lang.Object)
	 */
	@Override
	public boolean update(Bzgrade record) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.dao.AbstractDAO#getSelectStatementWithoutWhere()
	 */
	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_GRADE);		
		return sql.toString();
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.dao.AbstractDAO#getSelectStatementByIdentifier()
	 */
	@Override
	protected String getSelectStatementByIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.dao.AbstractDAO#getInsertStatement()
	 */
	@Override
	protected String getInsertStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see co.com.carpcosoftware.schoolmanagement.dao.AbstractDAO#getUpdateStatement()
	 */
	@Override
	protected String getUpdateStatement() {
		// TODO Auto-generated method stub
		return null;
	}

}
