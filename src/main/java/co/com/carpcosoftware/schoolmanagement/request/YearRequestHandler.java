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

import co.com.carpcosoftware.schoolmanagement.bll.YearBLL;
import co.com.carpcosoftware.schoolmanagement.entity.YearBO;
import co.com.carpcosoftware.schoolmanagement.util.ServiceLocator;

/**
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 29/06/2015
 */
@Path("/schoolmanagement/year/")
public class YearRequestHandler {
	
	@Autowired
	private YearBLL yearBLL = ServiceLocator.getBean(YearBLL.class);

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<YearBO> findAll() {
		return yearBLL.findAll();
	}
	
	@GET
	@Path("currentYear")
	@Produces(MediaType.APPLICATION_JSON)
	public YearBO findCurrentYear() {
		return yearBLL.findCurrentYear();
	}
}
