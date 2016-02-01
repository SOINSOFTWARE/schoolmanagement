package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
public class UserRequestHandler {

	@Autowired
	private UserBLL userBLL = ServiceLocator.getBean(UserBLL.class);
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<UserBO> findAll() {
		return userBLL.findAll();
	}
	
	@GET
	@Path("by")
	@Produces(MediaType.APPLICATION_JSON)
	public UserBO findBy(@QueryParam("id") int identifier, @QueryParam("documentNumber") String documentNumber) {
		UserBO userBO = null;
		if (documentNumber != null && !documentNumber.equals("")) {
			userBO = userBLL.findByCode(0, documentNumber, 0);
		} else {
			userBO = userBLL.findByIdentifier(identifier);
		}
		return userBO;
	}
	
	@GET
	@Path("byType")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<UserBO> findByType(@QueryParam("schoolId") int schoolId, @QueryParam("userTypeCode") String userTypeCode) {
		return userBLL.findByType(schoolId, userTypeCode);
	}
	
	@GET
	@Path("teacherFree")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<UserBO> findTeacherNoDirectors(@QueryParam("schoolId") int schoolId) {
		return userBLL.findTeacherNoGroupDirectors(schoolId);
	}
	
}
