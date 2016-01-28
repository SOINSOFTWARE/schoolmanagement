package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzclass;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 14/10/2015
 */
@Repository
public class ClassDAO extends AbstractDAO implements IDataAccesable<Bzclass> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzclass> select() {
		Set<Bzclass> bzClassSet = null;
		Chronometer chrono = this.startNewChronometer();        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzClassSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, ClassDAO.class.getName() + ", Select function");
        }        
        return bzClassSet;
	}

	@Override
	public Bzclass selectByIdentifier(Integer identifier) {
		Bzclass bzClass = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER, identifier);
        	bzClass = (query.list().isEmpty()) ? null : (Bzclass) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, ClassDAO.class.getName() + ", selectByIdentifier function");
        }
		return bzClass;
	}

	@Override
	public Bzclass selectByCode(String code) {
		return null;
	}

	@Override
	public void save(Bzclass record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, ClassRoomDAO.class.getName() + ", save function");
        }		
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_CLASS);		
		return sql.toString();
	}
}