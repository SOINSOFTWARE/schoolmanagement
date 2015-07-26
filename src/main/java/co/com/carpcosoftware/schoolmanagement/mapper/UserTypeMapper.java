/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.carpcosoftware.schoolmanagement.entity.UserTypeBO;

/**
 * User type mapper
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public class UserTypeMapper implements IJsonMappable<UserTypeBO> {
	
	/**
	 * Logger object
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(UserTypeMapper.class);

	@Override
	public UserTypeBO geObjectFromJSON(String objectAsJSON) {
		UserTypeBO userTypeBO = null;
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				userTypeBO = JSON_MAPPER.readValue(objectAsJSON, UserTypeBO.class);
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		
		return userTypeBO;
	}

}
