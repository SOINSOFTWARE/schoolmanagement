package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.soinsoftware.schoolmanagement.bll.SubjectBLL;
import co.com.soinsoftware.schoolmanagement.entity.SubjectBO;
import co.com.soinsoftware.schoolmanagement.util.ServiceLocator;

/**
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 28/01/2015
 */
@Path("/schoolmanagement/subject/")
public class SubjectRequestHandler extends AbstractRequestHandler {

	@Autowired
	private SubjectBLL subjectBLL = ServiceLocator.getBean(SubjectBLL.class);

	@GET
	@Path(PATH_ALL)
	@Produces(APPLICATION_JSON)
	public Set<SubjectBO> findAll() {
		final Set<SubjectBO> subjectSet = subjectBLL.findAll();
		LOGGER.info("findAll function loads {}", subjectSet.toString());
		return subjectSet;
	}

	@GET
	@Path(PATH_EXCLUDING_CLASS)
	@Produces(APPLICATION_JSON)
	public Set<SubjectBO> findExcludingCurrentClass(
			@QueryParam(PARAMETER_CLASSROOM_ID) final Integer idClassRoom) {
		final Set<SubjectBO> subjectSet = subjectBLL.findExcludingClass(idClassRoom);
		LOGGER.info("findExcludingCurrentClass function loads {}", subjectSet.toString());
		return subjectSet;
	}

	@POST
	@Path(PATH_SAVE)
	@Produces(APPLICATION_JSON)
	public SubjectBO save(String jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path(PATH_VALIDATE)
	@Produces(MediaType.TEXT_PLAIN)
	public String findByCode(final String code) {
		// TODO Auto-generated method stub
		return null;
	}
}