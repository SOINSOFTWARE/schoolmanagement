package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.soinsoftware.schoolmanagement.bll.UserBLL;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;
import co.com.soinsoftware.schoolmanagement.util.ServiceLocator;

/**
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 18/05/2015
 */
@Path("/schoolmanagement/user/")
public class UserRequestHandler extends AbstractRequestHandler {

	@Autowired
	private UserBLL userBLL = ServiceLocator.getBean(UserBLL.class);

	@GET
	@Path(PATH_ALL)
	@Produces(APPLICATION_JSON)
	public Set<UserBO> findAll() {
		final Set<UserBO> userSet = userBLL.findAll();
		LOGGER.info("findAll function loads {}", userSet.toString());
		return userSet;
	}

	@GET
	@Path(PATH_BY)
	@Produces(APPLICATION_JSON)
	public UserBO findBy(@QueryParam(PARAMETER_USER_ID) final int identifier,
			@QueryParam(PARAMETER_DOCUMENT_NUMBER) final String documentNumber) {
		UserBO user = null;
		if (documentNumber != null && !documentNumber.equals("")) {
			user = userBLL.findByCode(0, documentNumber, 0);
		} else {
			user = userBLL.findByIdentifier(identifier);
		}
		LOGGER.info("findBy function loads {}", user.toString());
		return user;
	}

	@GET
	@Path(PATH_BY_TYPE)
	@Produces(APPLICATION_JSON)
	public Set<UserBO> findByType(
			@QueryParam(PARAMETER_SCHOOL_ID) final int schoolId,
			@QueryParam(PARAMETER_USERTYPE_CODE) final String userTypeCode) {
		final Set<UserBO> userSet = userBLL.findByType(schoolId, userTypeCode);
		LOGGER.info("findByType function loads {}", userSet.toString());
		return userSet;
	}

	@GET
	@Path(PATH_TEACHERS_NOT_DIRECTORS)
	@Produces(APPLICATION_JSON)
	public Set<UserBO> findTeacherNotDirectors(
			@QueryParam(PARAMETER_SCHOOL_ID) final int schoolId) {
		final Set<UserBO> userSet = userBLL
				.findTeacherNoGroupDirectors(schoolId);
		LOGGER.info("findTeacherNotDirectors function loads {}",
				userSet.toString());
		return userSet;
	}

	@GET
	@Path(PATH_STUDENTS_NOT_LINKED)
	@Produces(APPLICATION_JSON)
	public Set<UserBO> findStudentsNotLinkedToClassRooms(
			@QueryParam(PARAMETER_CLASSROOM_ID) final Integer classRoomId,
			@QueryParam(PARAMETER_GRADE) final Integer grade,
			@QueryParam(PARAMETER_SCHOOL_ID) final int schoolId) {
		final Set<UserBO> userSet = userBLL.findStudentsNotLinkedToClassRoom(
				schoolId, grade, classRoomId);
		LOGGER.info("findStudentsNotLinkedToClassRooms function loads {}",
				userSet.toString());
		return userSet;
	}
}
