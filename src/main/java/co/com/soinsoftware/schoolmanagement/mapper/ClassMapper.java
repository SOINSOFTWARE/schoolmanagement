package co.com.soinsoftware.schoolmanagement.mapper;

import java.io.IOException;

import co.com.soinsoftware.schoolmanagement.entity.ClassBO;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 28/01/2016
 */
public class ClassMapper implements IJsonMappable<ClassBO> {

	@Override
	public ClassBO geObjectFromJSON(String objectAsJSON) {
		ClassBO classBO = null;
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				classBO = JSON_MAPPER.readValue(objectAsJSON, ClassBO.class);
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}		
		return classBO;
	}
}