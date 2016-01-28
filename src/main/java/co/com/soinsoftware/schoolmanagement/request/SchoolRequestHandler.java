/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

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
public class SchoolRequestHandler implements IRequestHandler<SchoolBO> {
	
	/**
	 * {@link SchoolBLL} object
	 */
	@Autowired
	private SchoolBLL schoolBLL = ServiceLocator.getBean(SchoolBLL.class);

	@Override
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<SchoolBO> findAll() {
		return schoolBLL.findAll();
	}
	
	@Override
	@GET
	@Path("validate")
	@Produces(MediaType.TEXT_PLAIN)
	public String findByCode(@QueryParam("code") String code) {
		boolean found = false;
		SchoolBO schoolBO = schoolBLL.findByCode(code);
		if (schoolBO != null) {
			found = true;
		}
		return Boolean.toString(found);
	}
	
	@Override
	@POST
	@Path("save")
	@Produces(MediaType.APPLICATION_JSON)
	public SchoolBO save(String jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GET
	@Path("by")
	@Produces(MediaType.APPLICATION_JSON)
	public SchoolBO findByIdentifier(@QueryParam("id") int identifier) {
		return schoolBLL.findByIdentifier(identifier);
	}
}
