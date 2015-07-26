/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzyear;
import co.com.carpcosoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
@Repository
public class YearDAO extends AbstractDAO implements IDataAccesable<Bzyear> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzyear> select() {
		Set<Bzyear> bzYearSet = null;
		Chronometer chrono = this.startNewChronometer();
        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzYearSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, YearDAO.class.getName() + ", Select function");
        }
        
        return bzYearSet;
	}

	@Override
	public Bzyear selectByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Bzyear newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Bzyear record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_YEAR);		
		return sql.toString();
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		// TODO Auto-generated method stub
		return null;
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

}
