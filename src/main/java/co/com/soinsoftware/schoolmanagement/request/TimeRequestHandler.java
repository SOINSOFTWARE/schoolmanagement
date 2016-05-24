/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.com.soinsoftware.schoolmanagement.bll.TimeBLL;
import co.com.soinsoftware.schoolmanagement.entity.TimeBO;

/**
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 27/08/2015
 */
@Path("/schoolmanagement/time/")
public class TimeRequestHandler {
	
	private final TimeBLL timeBLL = TimeBLL.getInstance();

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Set<TimeBO> findAll() {
		return timeBLL.findAll();
	}

}
