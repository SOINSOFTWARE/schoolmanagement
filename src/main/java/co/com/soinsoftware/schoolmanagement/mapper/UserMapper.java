/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.soinsoftware.schoolmanagement.entity.UserBO;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public class UserMapper implements IJsonMappable<UserBO> {
	
	/**
	 * Logger object
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(UserMapper.class);

	@Override
	public UserBO geObjectFromJSON(String objectAsJSON) {
		UserBO userBO = null;
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				userBO = JSON_MAPPER.readValue(objectAsJSON, UserBO.class);
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		
		return userBO;
	}

}
