/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.carpcosoftware.schoolmanagement.util.Chronometer;
import co.com.carpcosoftware.schoolmanagement.util.ServiceLocator;

/**
 * Abstract DAO <br/>
 * All DAO implementations must extend this class to support Spring integration
 * avoiding code repeatition
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 10/03/2015
 */
public abstract class AbstractDAO {
	
	public static final String TABLE_NAME_CLASSROOM = "Bzclassroom";
	public static final String TABLE_NAME_GRADE = "Bzgrade";
	public static final String TABLE_NAME_SCHOOL = "Bzschool";
	public static final String TABLE_NAME_TIME = "Bztime";	
	public static final String TABLE_NAME_USER = "Bzuser";
	public static final String TABLE_NAME_USERTYPE = "Cnusertype";
	public static final String TABLE_NAME_YEAR = "Bzyear";	
	
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_IDENTIFIER = "id";
	public static final String PARAMETER = "= :";

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractDAO.class);

	protected static final String STATEMENT_SELECT = " select ";
	protected static final String STATEMENT_FROM = " from ";
	protected static final String STATEMENT_JOIN = " join ";
	protected static final String STATEMENT_WHERE = " where ";
	protected static final String STATEMENT_INSERT = " insert into ";
	protected static final String STATEMENT_INSERT_VALUES = " values ";
	protected static final String STATEMENT_UPDATE = " update ";
	protected static final String STATEMENT_UPDATE_SET = " set ";
	
	/**
	 * Opens a new {@link Session} using session factory bean
	 * @return {@link Session} object
	 */
	private Session openSession() {
		SessionFactory sessioFactory = ServiceLocator.getBean("sessionFactory");
		Session session = sessioFactory.openSession();
		session.beginTransaction();
        return session;
    }
	
	/**
	 * Creates a new {@link Query} using the {@link Session} previously opened
	 * @param queryStatement Statement used to query database
	 * @return {@link Query} object
	 */
	public Query createQuery(String queryStatement) {
		return this.openSession().createQuery(queryStatement.toString());
        
	}
	
	public void save(java.io.Serializable object) {
		Session session = this.openSession();
		session.save(object);
		session.getTransaction().commit();
	}
	
	/**
	 * Creates and starts new {@link Chronometer} object
	 * @return {@link Chronometer} object
	 */
	public Chronometer startNewChronometer() {
		Chronometer chrono = new Chronometer();		
		chrono.start();
		return chrono;
	}
	
	/**
	 * Stops {@link Chronometer} object that was pass as parameter and also
	 * logs a new message to notify amount of time that chronometer was running
	 * @param chronometer {@link Chronometer} object previously started
	 * @param message Message to be logged
	 */
	public void stopChronometerAndLogMessage(Chronometer chronometer, String message) {
		chronometer.stop();
		LOGGER.info(message + ". Executed in: {}", chronometer.getTime());
	}

	/**
	 * Gets select statement that must be used in queries
	 * 
	 * @return Select statement
	 */
	protected abstract String getSelectStatementWithoutWhere();
	
	/**
	 * Gets select statement that must be used in select by identifier query
	 * 
	 * @return Select statement
	 */
	protected abstract String getSelectStatementByIdentifier();

	/**
	 * Gets insert statement
	 * 
	 * @return Insert statement
	 */
	protected abstract String getInsertStatement();

	/**
	 * Gets update statement
	 * 
	 * @return Update statement
	 */
	protected abstract String getUpdateStatement();
}