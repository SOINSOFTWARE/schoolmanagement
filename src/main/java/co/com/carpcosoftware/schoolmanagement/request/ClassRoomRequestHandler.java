/**
 * 
 */
package co.com.carpcosoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.carpcosoftware.schoolmanagement.bll.ClassRoomBLL;
import co.com.carpcosoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.carpcosoftware.schoolmanagement.util.ServiceLocator;

/**
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Path("/schoolmanagement/classroom/")
public class ClassRoomRequestHandler {
	
	@Autowired
	private ClassRoomBLL classRoomBLL = ServiceLocator.getBean(ClassRoomBLL.class);
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<ClassRoomBO> findAll() {
		return classRoomBLL.findAll();
	}
	
	@GET
	@Path("by")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<ClassRoomBO> findBy(@QueryParam("classRoomId") Integer classRoomId, @QueryParam("schoolId") int schoolId, 
			@QueryParam("year") String year, @QueryParam("grade") Integer grade) {
		return classRoomBLL.findBy(classRoomId, schoolId, year, grade);
	}
}
