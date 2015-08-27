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

import co.com.carpcosoftware.schoolmanagement.bll.TimeBLL;
import co.com.carpcosoftware.schoolmanagement.entity.TimeBO;
import co.com.carpcosoftware.schoolmanagement.util.ServiceLocator;

/**
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 27/08/2015
 */
@Path("/schoolmanagement/time/")
public class TimeRequestHandler {
	
	@Autowired
	private TimeBLL timeBLL = ServiceLocator.getBean(TimeBLL.class);

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Set<TimeBO> findAll() {
		return timeBLL.findAll();
	}

}
