package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroomxuser;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 14/10/2015
 */
@Repository
public class ClassRoomXUserDAO extends AbstractDAO implements IDataAccesable<Bzclassroomxuser> {
	
	public static final String COLUMN_IDENTIFIER_CLASSROOM = "idClassroom";
	public static final String COLUMN_IDENTIFIER_USER = "idUser";

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzclassroomxuser> select() {
		Set<Bzclassroomxuser> bzClassRoomXUserSet = null;
		Chronometer chrono = this.startNewChronometer();        
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzClassRoomXUserSet = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, ClassRoomXUserDAO.class.getName() + ", Select function");
        }
        return bzClassRoomXUserSet;
	}

	@Override
	public Bzclassroomxuser selectByIdentifier(Integer identifier) {
		return null;
	}

	@Override
	public Bzclassroomxuser selectByCode(String code) {
		return null;
	}
	
	public Bzclassroomxuser selectByIdentifier(Integer idClassRoom, Integer idUser) {
		Bzclassroomxuser bzClassRoomXUser = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER_CLASSROOM, idClassRoom);
        	query.setParameter(COLUMN_IDENTIFIER_USER, idUser);
        	bzClassRoomXUser = (Bzclassroomxuser) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, ClassRoomXUserDAO.class.getName() + ", selectByIdentifier function");
        }
		return bzClassRoomXUser;
	}

	@Override
	public void save(Bzclassroomxuser record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (this.selectByIdentifier(record.getBzclassroom().getId(), record.getBzuser().getId()) == null) 
					? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, ClassRoomDAO.class.getName() + ", save function");
        }
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_CLASSROOM_X_USER);		
		return sql.toString();
	}
	
	@Override
	protected String getSelectStatementByIdentifier() {
		StringBuilder sql = new StringBuilder(this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_IDENTIFIER_CLASSROOM);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_CLASSROOM);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_IDENTIFIER_USER);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_USER);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_ENABLED + " = 1");
		return sql.toString();		
	}
}