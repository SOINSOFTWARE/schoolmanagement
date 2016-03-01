package co.com.soinsoftware.schoolmanagement.request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
public class ClassRequestHandler extends AbstractRequestHandler {

	@Autowired
	private ClassBLL classBLL = ServiceLocator.getBean(ClassBLL.class);

	@GET
	@Path(PATH_ALL)
	@Produces(APPLICATION_JSON)
	public Set<ClassBO> findAll() {
		final Set<ClassBO> classSet = classBLL.findAll();
		LOGGER.info("findAll function loads {}", classSet.toString());
		return classSet;
	}
	
	@GET
	@Path(PATH_BY)
	@Produces(APPLICATION_JSON)
	public Set<ClassBO> findBy(
			@QueryParam(PARAMETER_CLASSROOM_ID) final int classRoomId,
			@QueryParam(PARAMETER_SCHOOL_ID) final int schoolId,
			@QueryParam(PARAMETER_USER_ID) final int idTeacher) {
		final Set<ClassBO> classSet = classBLL.findBy(schoolId, classRoomId, idTeacher);
		LOGGER.info("findBy function loads {}", classSet.toString());
		return classSet;
	}

	@POST
	@Path(PATH_SAVE)
	@Produces(APPLICATION_JSON)
	public Set<ClassBO> save(@FormParam(PARAMETER_OBJECT) final String jsonObject) {
		final Set<ClassBO> classSet = new HashSet<>();
		final List<ClassBO> classList = new ClassMapper().getObjectListFromJSON(jsonObject);
		if (!classList.isEmpty()) {
			for(final ClassBO classBO : classList) {
				final ClassBO savedClass = classBLL.saveRecord(classBO);
				classSet.add(savedClass);
				LOGGER.info("save function applied to {}", savedClass);
			}
		}
		return classSet;
	}
}