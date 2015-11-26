package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.carpcosoftware.schoolmanagement.hibernate.Bzschoolxuser;
import co.com.carpcosoftware.schoolmanagement.hibernate.BzschoolxuserId;
import co.com.carpcosoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 20/10/2015
 */
@Repository
public class SchoolXUserDAO extends AbstractDAO implements IDataAccesable<Bzschoolxuser> {
	
	public static final String COLUMN_IDENTIFIER_SCHOOL = "idSchool";
	public static final String COLUMN_IDENTIFIER_USER = "idUser";

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzschoolxuser> select() {
		Set<Bzschoolxuser> bzschoolxuserSet = null;
		Chronometer chrono = this.startNewChronometer();        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzschoolxuserSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, SchoolXUserDAO.class.getName() + ", Select function");
        }
        return bzschoolxuserSet;
	}

	@Override
	public Bzschoolxuser selectByIdentifier(Integer identifier) {
		return null;
	}

	@Override
	public Bzschoolxuser selectByCode(String code) {
		return null;
	}
	
	public Bzschoolxuser selectByIdentifier(Integer idSchool, Integer idUser) {
		Bzschoolxuser bzschoolxuser = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER_SCHOOL, idSchool);
        	query.setParameter(COLUMN_IDENTIFIER_USER, idUser);
        	bzschoolxuser = (Bzschoolxuser) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, SchoolXUserDAO.class.getName() + ", selectByIdentifier function");
        }
		return bzschoolxuser;
	}

	@Override
	public void save(Bzschoolxuser record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			BzschoolxuserId bzSchoolXUserId = record.getId();
			Bzschoolxuser bzSchoolXUser = this.selectByIdentifier(
					bzSchoolXUserId.getIdSchool(), bzSchoolXUserId.getIdUser());
			boolean isNew = (bzSchoolXUser == null) ? true : false;
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
		sql.append(TABLE_NAME_SCHOOL_X_USER);		
		return sql.toString();
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		StringBuilder sql = new StringBuilder(this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_IDENTIFIER_SCHOOL);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_SCHOOL);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_IDENTIFIER_USER);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_USER);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_ENABLED + " = 1");
		return sql.toString();		
	}
}
