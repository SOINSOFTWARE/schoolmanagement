package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzfinalnote;
import co.com.soinsoftware.schoolmanagement.hibernate.BzfinalnoteId;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 13/05/2016
 */
public class FinalNoteDAO extends AbstractDAO implements
		IDataAccesable<Bzfinalnote> {

	public static final String COLUMN_IDENTIFIER_CLASS = "idClass";
	public static final String COLUMN_IDENTIFIER_PERIOD = "idPeriod";
	public static final String COLUMN_IDENTIFIER_USER = "idUser";

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzfinalnote> select() {
		Set<Bzfinalnote> bzFinalNoteSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			final Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzFinalNoteSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono,
					FinalNoteDAO.class.getName() + ", Select function");
		}
		return bzFinalNoteSet;
	}

	@Override
	public Bzfinalnote selectByIdentifier(final Integer identifier) {
		return null;
	}

	@Override
	public Bzfinalnote selectByCode(final String code) {
		return null;
	}

	public Bzfinalnote selectByIdentifier(final Integer idClassRoom,
			final Integer idUser, final Integer idPeriod) {
		Bzfinalnote bzFinalNote = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			final Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER_CLASS, idClassRoom);
			query.setParameter(COLUMN_IDENTIFIER_USER, idUser);
			query.setParameter(COLUMN_IDENTIFIER_PERIOD, idPeriod);
			bzFinalNote = (query.list().isEmpty()) ? null : (Bzfinalnote) query
					.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono,
					FinalNoteDAO.class.getName()
							+ ", selectByIdentifier function");
		}
		return bzFinalNote;
	}

	@Override
	public void save(final Bzfinalnote record) {
		final Chronometer chrono = this.startNewChronometer();
		try {
			final BzfinalnoteId bzFinalNoteId = record.getId();
			final Bzfinalnote bzFinalNote = this.selectByIdentifier(
					bzFinalNoteId.getIdClass(), bzFinalNoteId.getIdUser(),
					bzFinalNoteId.getIdPeriod());
			final boolean isNew = (bzFinalNote == null) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono,
					FinalNoteDAO.class.getName() + ", save function");
		}
	}

	@SuppressWarnings("unchecked")
	public Set<Bzfinalnote> selectByClassAndPeriod(final Integer idClass,
			final Integer idPeriod) {
		Set<Bzfinalnote> bzFinalNoteSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			final Query query = session.createQuery(this
					.getSelectStatementByClassAndPeriod());
			query.setParameter(COLUMN_IDENTIFIER_CLASS, idClass);
			query.setParameter(COLUMN_IDENTIFIER_PERIOD, idPeriod);
			bzFinalNoteSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono,
					FinalNoteDAO.class.getName()
							+ ", selectByIdClassRoomAndPeriod function");
		}
		return bzFinalNoteSet;
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		final StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_FINALNOTE);
		return sql.toString();
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		final StringBuilder sql = new StringBuilder(
				this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_IDENTIFIER_CLASS);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_CLASS);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_IDENTIFIER_USER);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_USER);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_IDENTIFIER_PERIOD);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_PERIOD);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_ENABLED + " = 1");
		return sql.toString();
	}

	private String getSelectStatementByClassAndPeriod() {
		final StringBuilder sql = new StringBuilder(
				this.getSelectStatementWithoutWhere());
		sql.append(STATEMENT_WHERE);
		sql.append(COLUMN_IDENTIFIER_CLASS);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_CLASS);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_IDENTIFIER_PERIOD);
		sql.append(PARAMETER + COLUMN_IDENTIFIER_PERIOD);
		sql.append(STATEMENT_AND);
		sql.append(COLUMN_ENABLED + " = 1");
		return sql.toString();
	}
}