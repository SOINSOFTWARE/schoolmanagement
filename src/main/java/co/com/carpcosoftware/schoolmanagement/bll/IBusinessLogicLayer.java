/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.bll;

import java.util.Set;

/**
 * BO interface <br/>
 * Add here all methods that must be provided to implementation
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 10/03/2015
 */
public interface IBusinessLogicLayer<T> {
	
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
	public T findByIdentifier(Integer identifier);
	
	/**
	 * Select a record using its code
	 * 
	 * @param code
	 *            The code from object
	 * @return A record if code is found, null otherwise
	 */
	public T findByCode(String code);
	
	public boolean saveRecord(T record);

	/**
	 * Insert a new record
	 * 
	 * @param newRecord
	 *            Object to be inserted
	 */
	public boolean insertRecord(T newRecord);

	/**
	 * Update a record
	 * 
	 * @param record
	 *            Object to be updated
	 */
	public boolean updateRecord(T record);
	
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
	public T selectByIdentifierAndPutInCache(Integer identifier);
	
	public Set<T> createEntityBOSetUsingHibernatEntities(Set<?> hibernateEntitySet);
}
