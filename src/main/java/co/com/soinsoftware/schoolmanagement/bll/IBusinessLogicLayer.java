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
	
	public Set<T> findAll();
	
	public Set<T> findAll(final int idSchool);

	public T findByIdentifier(final Integer identifier);
	
	public T findByCode(final int idSchool, final String code, final int identifier);
	
	public T saveRecord(final T record);
	
	public Set<T> selectAndPutInCache();
	
	public T selectByIdentifierAndPutInCache(final Integer identifier);
	
	public Set<T> createEntityBOSetUsingHibernatEntities(final Set<?> hibernateEntitySet);
	
	public T putObjectInCache(final E record);
	
	public E buildHibernateEntity(final T record);
}
