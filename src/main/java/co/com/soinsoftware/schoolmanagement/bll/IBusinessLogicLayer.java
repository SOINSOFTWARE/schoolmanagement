/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.bll;

import java.util.Set;

/**
 * BO interface <br/>
 * Add here all methods that must be provided to implementation
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 10/03/2015
 */
public interface IBusinessLogicLayer<T, E> {
	
	/**
	 * Finds all records
	 * 
	 * @return Set of objects
	 */
	public Set<T> findAll();

	/**
	 * Finds a record using identifier
	 * 
	 * @param identifier
	 *            The identifier from object
	 * @return A record if identifier is found, null otherwise
	 */
	public T findByIdentifier(final Integer identifier);
	
	/**
	 * Select a record using its code
	 * 
	 * @param code
	 *            The code from object
	 * @return A record if code is found, null otherwise
	 */
	public T findByCode(final String code);
	
	public T saveRecord(final T record);

	/**
	 * Insert a new record
	 * 
	 * @param newRecord
	 *            Object to be inserted
	 */
	public T insertRecord(final T newRecord);

	/**
	 * Update a record
	 * 
	 * @param record
	 *            Object to be updated
	 */
	public T updateRecord(final T record);
	
	/**
	 * Selects all records from database and put them into cache
	 * 
	 * @return Set of objects
	 */
	public Set<T> selectAndPutInCache();
	
	/**
	 * Selects a record using identifier and put it into cache
	 * 
	 * @param identifier
	 *            The identifier from object
	 * @return A record if identifier is found, null otherwise
	 */
	public T selectByIdentifierAndPutInCache(final Integer identifier);
	
	public Set<T> createEntityBOSetUsingHibernatEntities(final Set<?> hibernateEntitySet);
	
	public T putObjectInCache(final E record);
	
	public E buildHibernateEntity(final T record);
}
