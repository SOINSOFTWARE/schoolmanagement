/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.mapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * JSON mappable interface
 * 
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public interface IJsonMappable<T> {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(IJsonMappable.class);
	
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
