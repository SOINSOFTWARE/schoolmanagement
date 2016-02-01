package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
public class SubjectRequestHandler {

	@Autowired
	private SubjectBLL subjectBLL = ServiceLocator.getBean(SubjectBLL.class);

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<SubjectBO> findAll() {
		return subjectBLL.findAll();
	}

	@POST
	@Path("save")
	@Produces(MediaType.APPLICATION_JSON)
	public SubjectBO save(String jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("validate")
	@Produces(MediaType.TEXT_PLAIN)
	public String findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}
}