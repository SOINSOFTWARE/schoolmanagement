/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.Set;

/**
 * DAO interface <br/>
 * Add here all methods that must be provided to DAO implementation
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 09/03/2015
 */
public interface IDataAccesable<T> {

	/**
	 * Selects all records from database
	 * 
	 * @return User set
	 */
	public Set<T> select();

	/**
	 * Select a record using identifier
	 * 
	 * @param identifier
	 *            The identifier from database
	 * @return A record if identifier is found in database, null otherwise
	 */
	public T selectByIdentifier(Integer identifier);

	/**
	 * Insert a new record in database
	 * 
	 * @param newRecord
	 *            Object to be inserted
	 */
	public boolean insert(T newRecord);

	/**
	 * Update a record in database
	 * 
	 * @param record
	 *            Object to be updated
	 */
	public boolean update(T record);

}
