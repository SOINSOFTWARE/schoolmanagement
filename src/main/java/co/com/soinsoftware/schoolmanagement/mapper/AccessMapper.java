/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.soinsoftware.schoolmanagement.entity.AccessBO;

/**
 * Access mapper
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public class AccessMapper implements IJsonMappable<AccessBO> {
	
	/**
	 * Logger object
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(AccessMapper.class);

	@Override
	public AccessBO geObjectFromJSON(String objectAsJSON) {
		AccessBO accessBO = null;
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				accessBO = JSON_MAPPER.readValue(objectAsJSON, AccessBO.class);
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		
		return accessBO;
	}
}
