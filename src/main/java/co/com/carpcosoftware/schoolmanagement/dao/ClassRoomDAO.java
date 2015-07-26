/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzclassroom;
import co.com.carpcosoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Repository
public class ClassRoomDAO extends AbstractDAO implements IDataAccesable<Bzclassroom> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzclassroom> select() {
		Set<Bzclassroom> bzClassRoomSet = null;
		Chronometer chrono = this.startNewChronometer();
        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzClassRoomSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, ClassRoomDAO.class.getName() + ", Select function");
        }
        
        return bzClassRoomSet;
	}

	@Override
	public Bzclassroom selectByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Bzclassroom newRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Bzclassroom record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_CLASSROOM);		
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
