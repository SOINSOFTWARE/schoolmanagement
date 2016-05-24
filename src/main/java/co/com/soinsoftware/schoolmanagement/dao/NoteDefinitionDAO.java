package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.schoolmanagement.hibernate.Bznotedefinition;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 15/10/2015
 */
public class NoteDefinitionDAO extends AbstractDAO implements IDataAccesable<Bznotedefinition> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bznotedefinition> select() {
		Set<Bznotedefinition> bzNoteDefinition = null;
		Chronometer chrono = this.startNewChronometer();       
        try {
            Query query = this.createQuery(this.getSelectStatementWithoutWhere());
            bzNoteDefinition = new HashSet<>(query.list());
        } catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, NoteDefinitionDAO.class.getName() + ", Select function");
        }        
        return bzNoteDefinition;
	}

	@Override
	public Bznotedefinition selectByIdentifier(Integer identifier) {
		Bznotedefinition bzNoteDefinition = null;
		Chronometer chrono = this.startNewChronometer();
		try {
        	Query query = this.createQuery(this.getSelectStatementByIdentifier());
        	query.setParameter(COLUMN_IDENTIFIER, identifier);
        	bzNoteDefinition = (Bznotedefinition) query.list().get(0);
        } catch (HibernateException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            this.stopChronometerAndLogMessage(chrono, NoteDefinitionDAO.class.getName() + ", selectByIdentifier function");
        }
		return bzNoteDefinition;
	}

	@Override
	public Bznotedefinition selectByCode(String code) {
		return null;
	}

	@Override
	public void save(Bznotedefinition record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
        	LOGGER.error(ex.getMessage());
        } finally {
            chrono.stop();
            this.stopChronometerAndLogMessage(chrono, NoteDefinitionDAO.class.getName() + ", save function");
        }
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_NOTEDEFINITION);		
		return sql.toString();
	}

}
