/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.carpcosoftware.schoolmanagement.bll.SchoolBLL;
import co.com.carpcosoftware.schoolmanagement.entity.SchoolBO;
import co.com.carpcosoftware.schoolmanagement.mapper.SchoolMapper;
import co.com.carpcosoftware.schoolmanagement.util.ServiceLocator;

/**
 * School request handler
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 10/03/2015
 */
@Path("/schoolmanagement/school/")
public class SchoolRequestHandler {
	
	/**
	 * {@link SchoolBLL} object
	 */
	@Autowired
	private SchoolBLL schoolBLL = ServiceLocator.getBean(SchoolBLL.class);

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<SchoolBO> findAll() {
		return schoolBLL.findAll();
	}
	
	@GET
	@Path("by")
	@Produces(MediaType.APPLICATION_JSON)
	public SchoolBO findByIdentifier(@QueryParam("id") int identifier) {
		return schoolBLL.findByIdentifier(identifier);
	}
	
	@POST
	@Path("insert")
	@Produces(MediaType.TEXT_PLAIN)
	public String insert(@QueryParam("school") String schoolJSON) {
		boolean success = false;
		SchoolBO newRecord = new SchoolMapper().geObjectFromJSON(schoolJSON);
		if (newRecord != null) {
			success = schoolBLL.insertRecord(newRecord);
		}
		return Boolean.toString(success);
	}
	
	@POST
	@Path("update")
	@Produces(MediaType.TEXT_PLAIN)
	public String update(@QueryParam("school") String schoolJSON) {
		boolean success = false;
		SchoolBO record = new SchoolMapper().geObjectFromJSON(schoolJSON);
		if (record != null) {
			success = schoolBLL.updateRecord(record);
		}
		return Boolean.toString(success);
	}
	
	@GET
	@Path("validate")
	@Produces(MediaType.TEXT_PLAIN)
	public String findByIdentifier(@QueryParam("code") String code) {
		boolean found = false;
		SchoolBO schoolBO = schoolBLL.findByCode(code);
		if (schoolBO != null) {
			found = true;
		}
		return Boolean.toString(found);
	}
}
