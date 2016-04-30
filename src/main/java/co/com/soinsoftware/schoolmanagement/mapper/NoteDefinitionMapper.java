/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import co.com.soinsoftware.schoolmanagement.entity.NoteDefinitionBO;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 26/04/2016
 */
public class NoteDefinitionMapper implements IJsonMappable<NoteDefinitionBO> {

	@Override
	public NoteDefinitionBO geObjectFromJSON(final String objectAsJSON) {
		NoteDefinitionBO noteDefinition = null;
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				noteDefinition = JSON_MAPPER.readValue(objectAsJSON,
						NoteDefinitionBO.class);
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		return noteDefinition;
	}

	public List<NoteDefinitionBO> getObjectListFromJSON(
			final String objectAsJSON) {
		List<NoteDefinitionBO> noteDefList = new ArrayList<>();
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				noteDefList = JSON_MAPPER.readValue(objectAsJSON,
						new TypeReference<List<NoteDefinitionBO>>() {
						});
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		return noteDefList;
	}
}
