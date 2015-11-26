package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzsubject;
import co.com.carpcosoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 20/10/2015
 */
@Repository
public class SubjectDAO extends AbstractDAO implements IDataAccesable<Bzsubject> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzsubject> select() {
		Set<Bzsubject> bzSubjectSet = null;
		Chronometer chrono = this.startNewChronometer();
		
        try {
        	Query query = this.createQuery(this.getSelectStatementWithoutWhere());
        	bzSubjectSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, SubjectDAO.class.getName() + ", Select function");
        }
		return bzSubjectSet;
	}

	@Override
	public Bzsubject selectByIdentifier(Integer identifier) {
		Bzsubject bzSubject = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER, identifier);
        	bzSubject = (Bzsubject) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, SubjectDAO.class.getName() + ", selectByIdentifier function");
        }
		return bzSubject;
	}

	@Override
	public Bzsubject selectByCode(String code) {
		Bzsubject bzSubject = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByCode());
        	query.setParameter(COLUMN_CODE, code);
        	bzSubject = (Bzsubject) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, SubjectDAO.class.getName() + ", selectByCode function");
        }
		return bzSubject;
	}

	@Override
	public void save(Bzsubject record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, SubjectDAO.class.getName() + ", save function");
        }
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_SUBJECT);
		return sql.toString();
	}

}
