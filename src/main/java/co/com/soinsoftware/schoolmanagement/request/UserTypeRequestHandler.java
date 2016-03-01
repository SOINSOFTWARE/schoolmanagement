package co.com.soinsoftware.schoolmanagement.request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.soinsoftware.schoolmanagement.bll.UserTypeBLL;
import co.com.soinsoftware.schoolmanagement.entity.UserTypeBO;
import co.com.soinsoftware.schoolmanagement.util.ServiceLocator;

/**
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 26/02/2016
 */
@Path("/schoolmanagement/usertype/")
public class UserTypeRequestHandler extends AbstractRequestHandler {

	@Autowired
	private UserTypeBLL userTypeBLL = ServiceLocator.getBean(UserTypeBLL.class);

	@GET
	@Path(PATH_BY)
	@Produces(APPLICATION_JSON)
	public UserTypeBO findBy(@QueryParam(PARAMETER_CODE) final String code) {
		final UserTypeBO userType = this.userTypeBLL.findByCode(0, code, 0);
		if (userType != null) {
			LOGGER.info("findBy function loads {}", userType.toString());
		}
		return userType;
	}
}