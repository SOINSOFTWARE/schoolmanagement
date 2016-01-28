package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.soinsoftware.schoolmanagement.bll.ClassRoomBLL;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.mapper.ClassRoomMapper;
import co.com.soinsoftware.schoolmanagement.util.ServiceLocator;

/**
 * *
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 05/06/2015
 */
@Path("/schoolmanagement/classroom/")
public class ClassRoomRequestHandler implements IRequestHandler<ClassRoomBO> {

	@Autowired
	private ClassRoomBLL classRoomBLL = ServiceLocator
			.getBean(ClassRoomBLL.class);

	@Override
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<ClassRoomBO> findAll() {
		return classRoomBLL.findAll();
	}

	@GET
	@Path("by")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<ClassRoomBO> findBy(
			@QueryParam("classRoomId") Integer classRoomId,
			@QueryParam("schoolId") int schoolId,
			@QueryParam("year") String year,
			@QueryParam("grade") Integer grade, @QueryParam("time") Integer time) {
		return classRoomBLL.findBy(classRoomId, schoolId, year, grade, time);
	}

	@Override
	@POST
	@Path("save")
	@Produces(MediaType.APPLICATION_JSON)
	public ClassRoomBO save(@FormParam("object") String jsonObject) {
		ClassRoomBO classRoomBO = null;
		ClassRoomBO newClassRoomBO = new ClassRoomMapper()
				.geObjectFromJSON(jsonObject);
		if (newClassRoomBO != null) {
			classRoomBO = classRoomBLL.saveRecord(newClassRoomBO);
		}
		return classRoomBO;
	}

	@Override
	@GET
	@Path("validate")
	@Produces(MediaType.TEXT_PLAIN)
	public String findByCode(@QueryParam("code") String code) {
		boolean found = false;
		ClassRoomBO classRoomBO = classRoomBLL.findByCode(code);
		if (classRoomBO != null) {
			found = true;
		}
		return Boolean.toString(found);
	}
}
