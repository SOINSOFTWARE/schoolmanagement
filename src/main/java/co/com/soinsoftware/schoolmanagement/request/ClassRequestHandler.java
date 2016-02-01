package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.soinsoftware.schoolmanagement.bll.ClassBLL;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.mapper.ClassMapper;
import co.com.soinsoftware.schoolmanagement.util.ServiceLocator;

/**
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 28/01/2016
 */
@Path("/schoolmanagement/class/")
public class ClassRequestHandler {

	@Autowired
	private ClassBLL classBLL = ServiceLocator.getBean(ClassBLL.class);

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<ClassBO> findAll() {
		return classBLL.findAll();
	}

	@POST
	@Path("save")
	@Produces(MediaType.APPLICATION_JSON)
	public ClassBO save(String jsonObject) {
		ClassBO classBO = null;
		ClassBO newClassBO = new ClassMapper().geObjectFromJSON(jsonObject);
		if (newClassBO != null) {
			classBO = classBLL.saveRecord(newClassBO);
		}
		return classBO;
	}
}