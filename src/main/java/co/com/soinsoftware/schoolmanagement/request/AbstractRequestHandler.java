package co.com.soinsoftware.schoolmanagement.request;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 03/02/2015
 */
public abstract class AbstractRequestHandler {

	public static final String PATH_ALL = "all";
	public static final String PATH_BY = "by";
	public static final String PATH_BY_TYPE = "byType";
	public static final String PATH_EXCLUDING_CLASS = "excludingClass";
	public static final String PATH_NOTEDEFINITION_BY_CLASS = "noteDefByClass";
	public static final String PATH_SAVE = "save";
	public static final String PATH_SAVE_CLASSROOM_X_STUDENT = "saveClassRoomxStudent";
	public static final String PATH_SAVE_NOTEDEFINITION_BY_CLASS = "saveNoteDefByClass";
	public static final String PATH_SAVE_NOTEVALUE = "saveNoteValue";
	public static final String PATH_STUDENTS_NOT_LINKED = "studentsNotLinked";
	public static final String PATH_TEACHERS_NOT_DIRECTORS = "teacherNotDirectors";
	public static final String PATH_VALIDATE = "validate";

	public static final String PARAMETER_CLASS_ID = "classId";
	public static final String PARAMETER_CLASSROOM_ID = "classRoomId";
	public static final String PARAMETER_CODE = "code";
	public static final String PARAMETER_DOCUMENT_NUMBER = "documentNumber";
	public static final String PARAMETER_GRADE = "grade";
	public static final String PARAMETER_OBJECT = "object";
	public static final String PARAMETER_PERIOD_ID = "periodId";
	public static final String PARAMETER_SCHOOL_ID = "schoolId";
	public static final String PARAMETER_TIME = "time";
	public static final String PARAMETER_USER_ID = "userId";
	public static final String PARAMETER_USERTYPE_CODE = "userTypeCode";
	public static final String PARAMETER_YEAR = "year";

	public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON
			+ "; charset=UTF-8";

	public static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractRequestHandler.class);

}