/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.carpcosoftware.schoolmanagement.bll.GradeBLL;
import co.com.carpcosoftware.schoolmanagement.entity.GradeBO;
import co.com.carpcosoftware.schoolmanagement.util.ServiceLocator;

/**
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
@Path("/schoolmanagement/grade/")
public class GradeRequestHandler {

	@Autowired
	private GradeBLL gradeBLL = ServiceLocator.getBean(GradeBLL.class);

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<GradeBO> findAll() {
		return gradeBLL.findAll();
	}
}
