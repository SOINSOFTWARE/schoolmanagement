/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.carpcosoftware.schoolmanagement.entity.ClassRoomBO;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 28/07/2015
 */
public class ClassRoomMapper implements IJsonMappable<ClassRoomBO> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomMapper.class);

	@Override
	public ClassRoomBO geObjectFromJSON(String objectAsJSON) {
		ClassRoomBO classRoomBO = null;
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				classRoomBO = JSON_MAPPER.readValue(objectAsJSON, ClassRoomBO.class);
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		
		return classRoomBO;
	}

}
