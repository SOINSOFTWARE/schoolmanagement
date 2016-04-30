package co.com.soinsoftware.schoolmanagement.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import co.com.soinsoftware.schoolmanagement.entity.NoteValueBO;

public class NoteValueMapper implements IJsonMappable<NoteValueBO> {

	@Override
	public NoteValueBO geObjectFromJSON(String objectAsJSON) {
		NoteValueBO noteValue = null;
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				noteValue = JSON_MAPPER.readValue(objectAsJSON,
						NoteValueBO.class);
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		return noteValue;
	}

	public List<NoteValueBO> getObjectListFromJSON(final String objectAsJSON) {
		List<NoteValueBO> noteValueList = new ArrayList<>();
		if (objectAsJSON != null && !objectAsJSON.equals("")) {
			try {
				noteValueList = JSON_MAPPER.readValue(objectAsJSON,
						new TypeReference<List<NoteValueBO>>() {
						});
			} catch (IOException ex) {
				LOGGER.error(ex.getMessage());
			}
		}
		return noteValueList;
	}
}
