/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.schoolmanagement.hibernate.Bzclassroom;
import co.com.soinsoftware.schoolmanagement.util.Chronometer;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
public class ClassRoomDAO extends AbstractDAO implements
		IDataAccesable<Bzclassroom> {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Bzclassroom> select() {
		Set<Bzclassroom> bzClassRoomSet = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementWithoutWhere());
			bzClassRoomSet = new HashSet<>(query.list());
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono,
					ClassRoomDAO.class.getName() + ", Select function");
		}
		return bzClassRoomSet;
	}

	@Override
	public Bzclassroom selectByIdentifier(Integer identifier) {
		Bzclassroom bzClassRoom = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this
					.getSelectStatementByIdentifier());
			query.setParameter(COLUMN_IDENTIFIER, identifier);
			bzClassRoom = (query.list().isEmpty()) ? null : (Bzclassroom) query
					.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono,
					ClassRoomDAO.class.getName()
							+ ", selectByIdentifier function");
		}
		return bzClassRoom;
	}

	@Override
	public Bzclassroom selectByCode(String code) {
		Bzclassroom bzClassRoom = null;
		final Chronometer chrono = this.startNewChronometer();
		final Session session = this.openSession();
		try {
			Query query = session.createQuery(this.getSelectStatementByCode());
			query.setParameter(COLUMN_CODE, code);
			bzClassRoom = (query.list().isEmpty()) ? null : (Bzclassroom) query
					.list().get(0);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			session.disconnect();
			this.stopChronometerAndLogMessage(chrono,
					ClassRoomDAO.class.getName() + ", selectByCode function");
		}
		return bzClassRoom;
	}

	@Override
	public void save(Bzclassroom record) {
		Chronometer chrono = this.startNewChronometer();
		try {
			boolean isNew = (record.getId() == 0) ? true : false;
			this.save(record, isNew);
		} catch (HibernateException ex) {
			LOGGER.error(ex.getMessage());
		} finally {
			chrono.stop();
			this.stopChronometerAndLogMessage(chrono,
					ClassRoomDAO.class.getName() + ", save function");
		}
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		StringBuilder sql = new StringBuilder();
		sql.append(STATEMENT_FROM);
		sql.append(TABLE_NAME_CLASSROOM);
		return sql.toString();
	}
}