/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.soinsoftware.schoolmanagement.bll.SchoolBLL;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.util.ServiceLocator;

/**
 * School request handler
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 10/03/2015
 */
@Path("/schoolmanagement/school/")
public class SchoolRequestHandler extends AbstractRequestHandler {

	@Autowired
	private SchoolBLL schoolBLL = ServiceLocator.getBean(SchoolBLL.class);

	@GET
	@Path(PATH_ALL)
	@Produces(APPLICATION_JSON)
	public Set<SchoolBO> findAll() {
		final Set<SchoolBO> schoolSet = schoolBLL.findAll();
		if (schoolSet != null) {
			LOGGER.info("findAll function loads {}", schoolSet.toString());
		}
		return schoolSet;
	}
	
	@GET
	@Path(PATH_BY)
	@Produces(APPLICATION_JSON)
	public SchoolBO findByIdentifier(
			@QueryParam(PARAMETER_SCHOOL_ID) final int identifier) {
		final SchoolBO school = this.schoolBLL.findByIdentifier(identifier);
		if (school != null) {
			LOGGER.info("findBy function loads {}", school.toString());
		}
		return school;
	}

	@GET
	@Path(PATH_VALIDATE)
	@Produces(MediaType.TEXT_PLAIN)
	public String validateCode(
			@QueryParam(PARAMETER_SCHOOL_ID) final int identifier,
			@QueryParam(PARAMETER_CODE) final String code) {
		boolean validCode = false;
		SchoolBO schoolBO = schoolBLL.findByCode(0, code, 0);
		if (schoolBO == null
				|| (schoolBO != null && schoolBO.getId().equals(identifier))) {
			validCode = true;
		}
		LOGGER.info("code {} is valid = {}", code, validCode);
		return Boolean.toString(validCode);
	}

	@POST
	@Path(PATH_SAVE)
	@Produces(APPLICATION_JSON)
	public SchoolBO save(@FormParam(PARAMETER_OBJECT) final String jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}
}