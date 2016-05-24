package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.com.soinsoftware.schoolmanagement.bll.UserBLL;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;
import co.com.soinsoftware.schoolmanagement.mapper.UserMapper;

/**
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 18/05/2015
 */
@Path("/schoolmanagement/user/")
public class UserRequestHandler extends AbstractRequestHandler {

	private final UserBLL userBLL = UserBLL.getInstance();

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
		if (user != null) {
			LOGGER.info("findBy function loads {}", user.toString());
		}
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

	@POST
	@Path(PATH_SAVE)
	@Produces(APPLICATION_JSON)
	public UserBO save(@FormParam(PARAMETER_OBJECT) final String jsonObject) {
		UserBO savedUser = null;
		final UserBO user = new UserMapper().geObjectFromJSON(jsonObject);
		if (user != null) {
			savedUser = this.userBLL.saveRecord(user);
			LOGGER.info("save function applied to {}", savedUser);
		}
		return savedUser;
	}

	@GET
	@Path(PATH_VALIDATE)
	@Produces(MediaType.TEXT_PLAIN)
	public String validateExistingByDocumentNumber(
			@QueryParam(PARAMETER_USER_ID) final int identifier,
			@QueryParam(PARAMETER_DOCUMENT_NUMBER) final String documentNumber,
			@QueryParam(PARAMETER_SCHOOL_ID) final int schoolId) {
		boolean validCode = false;
		final UserBO user = userBLL.findByCode(0, documentNumber, 0);
		boolean validId = (user != null && user.getId().equals(identifier));
		boolean isLinkedToSchool = this.userBLL
				.isLinkedToSchool(user, schoolId);
		if (user == null || (validId && isLinkedToSchool)
				|| (!validId && !isLinkedToSchool)) {
			validCode = true;
		}
		LOGGER.info("documentNumber {} is valid = {}", documentNumber,
				validCode);
		return Boolean.toString(validCode);
	}
	
	@GET
	@Path(PATH_STUDENTS_BY_GUARDIAN)
	@Produces(APPLICATION_JSON)
	public Set<UserBO> findStudentsByGuardian(@QueryParam(PARAMETER_USER_ID) final Integer idGuardian) {
		final Set<UserBO> userSet = this.userBLL.selectByGuardian(idGuardian);
		if (userSet != null) {
			LOGGER.info("findStudentsByGuardian function loads {}", userSet.toString());
		}
		return userSet;
	}
}