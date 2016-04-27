package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import co.com.soinsoftware.schoolmanagement.hibernate.Bznotevalue;
import co.com.soinsoftware.schoolmanagement.hibernate.BznotevalueId;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 15/10/2015
 */
@Repository
public class NoteValueDAO extends AbstractDAO implements
		IDataAccesable<Bznotevalue> {

	public static final String COLUMN_IDENTIFIER_NOTEDEFINITION = "idNoteDefinition";
	public static final String COLUMN_IDENTIFIER_USER = "idUser";

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bznotevalue> select() {
		Set<Bznotevalue> bzNoteDefinitionSet = null;
		Chronometer chrono = this.startNewChronometer();
		try {
			Query query = this.createQuery(this
					.getSelectStatementWithoutWhere());
			bzNoteDefinitionSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono,
					NoteValueDAO.class.getName() + ", Select function");
		}
		return bzNoteDefinitionSet;
	}

	@Override
	public Bznotevalue selectByIdentifier(Integer identifier) {
		return null;
	}

	@Override
	public Bznotevalue selectByCode(String code) {
		return null;
	}

	public Bznotevalue selectByIdentifier(Integer idNoteDefinition,
			Integer idUser) {
		Bznotevalue bzNoteValue = null;
		Chronometer chrono = this.startNewChronometer();
		try {
			Query query = this.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER_NOTEDEFINITION,
					idNoteDefinition);
			query.setParameter(COLUMN_IDENTIFIER_USER, idUser);
			bzNoteValue = (Bznotevalue) query.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			this.stopChronometerAndLogMessage(chrono,
					NoteValueDAO.class.getName()
							+ ", selectByIdentifier function");
		}
		return bzNoteValue;
	}

	@Override
	public void save(Bznotevalue record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			BznotevalueId bzNoteValueId = record.getId();
			Bznotevalue bzNoteValue = this.selectByIdentifier(
					bzNoteValueId.getIdNoteDefinition(),
					bzNoteValueId.getIdUser());
			boolean isNew = (bzNoteValue == null) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono,
					NoteValueDAO.class.getName() + ", save function");
		}
	}

	@SuppressWarnings("unchecked")
	public Set<Bznotevalue> selectByIdNoteDefinition(
			final Integer idNoteDefinition) {
		Set<Bznotevalue> bzNoteValueSet = null;
		Chronometer chrono = this.startNewChronometer();
		try {
			final StringBuilder queryStr = new StringBuilder(
					this.getSelectStatementWithoutWhere());
			queryStr.append(STATEMENT_WHERE);
			queryStr.append(COLUMN_IDENTIFIER_NOTEDEFINITION);
			queryStr.append(PARAMETER + COLUMN_IDENTIFIER_NOTEDEFINITION);
			final Query query = this.createQuery(queryStr.toString());
			query.setParameter(COLUMN_IDENTIFIER_NOTEDEFINITION,
					idNoteDefinition);
			bzNoteValueSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			this.stopChronometerAndLogMessage(chrono,
					NoteValueDAO.class.getName()
							+ ", selectByIdNoteDefinition function");
		}
		return bzNoteValueSet;
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_NOTEVALUE);
		return sql.toString();
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		StringBuilder sql = new StringBuilder(
				this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_IDENTIFIER_NOTEDEFINITION);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_NOTEDEFINITION);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_IDENTIFIER_USER);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_USER);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_ENABLED + " = 1");
		return sql.toString();
	}

}