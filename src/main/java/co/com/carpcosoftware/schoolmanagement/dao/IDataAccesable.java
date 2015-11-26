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
	 * Select a record using its identifier column
	 * 
	 * @param identifier Value of identifier column from database
	 * @return A record if identifier value is found in database, null otherwise
	 */
	public T selectByIdentifier(Integer identifier);
	
	/**
	 * Selects a record using its code column
	 * 
	 * @param code Value of code column from database
	 * @return A record if code value is found in database, null otherwise
	 */
	public T selectByCode(String code);

	/**
	 * Inserts or Updates a record into database
	 * 
	 * @param record Object to be saved
	 */
	public void save(T record);
}
