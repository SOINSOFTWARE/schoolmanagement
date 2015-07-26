/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.mapper;

import org.codehaus.jackson.map.ObjectMapper;


/**
 * JSON mappable interface
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public interface IJsonMappable<T> {
	
	/**
	 * JSON object mapper
	 */
	static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	/**
	 * Gets object from JSON string
	 * @param objectAsJSON object as a JSON string
	 * @return Object mapped
	 */
	public T geObjectFromJSON(String objectAsJSON);
}
