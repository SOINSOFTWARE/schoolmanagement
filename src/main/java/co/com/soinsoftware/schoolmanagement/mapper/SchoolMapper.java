/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;

/**
 * School mapper
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 17/03/2015
 */
public class SchoolMapper implements IJsonMappable<SchoolBO> {
	
	/**
	 * Logger object
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(SchoolMapper.class);

	@Override
	public SchoolBO geObjectFromJSON(String objectAsJSON) {
		SchoolBO schoolBO = null;
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				schoolBO = JSON_MAPPER.readValue(objectAsJSON, SchoolBO.class);
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		
		return schoolBO;
	}
}
