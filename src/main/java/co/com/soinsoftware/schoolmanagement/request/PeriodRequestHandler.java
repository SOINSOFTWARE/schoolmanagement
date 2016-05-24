/**
 * 
 */
package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import co.com.soinsoftware.schoolmanagement.bll.PeriodBLL;
import co.com.soinsoftware.schoolmanagement.entity.PeriodBO;

/**
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 21/04/2016
 */
@Path("/schoolmanagement/period/")
public class PeriodRequestHandler extends AbstractRequestHandler {

	private final PeriodBLL periodBLL = PeriodBLL.getInstance();

	@GET
	@Path(PATH_ALL)
	@Produces(APPLICATION_JSON)
	public Set<PeriodBO> findAll(
			@QueryParam(PARAMETER_SCHOOL_ID) final int idSchool) {
		final Set<PeriodBO> periodSet = periodBLL.findAll(idSchool);
		LOGGER.info("findAll function loads {}", periodSet.toString());
		return periodSet;
	}
}