package co.com.soinsoftware.schoolmanagement.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

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

	public List<ClassBO> getObjectListFromJSON(String objectAsJSON) {
		List<ClassBO> classList = new ArrayList<>();
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				classList = JSON_MAPPER.readValue(
						objectAsJSON, new TypeReference<List<ClassBO>>() {
						});
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		return classList;
	}
}