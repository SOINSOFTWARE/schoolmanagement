/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.mapper;

import java.io.IOException;

import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 28/07/2015
 */
public class ClassRoomMapper implements IJsonMappable<ClassRoomBO> {
	
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
