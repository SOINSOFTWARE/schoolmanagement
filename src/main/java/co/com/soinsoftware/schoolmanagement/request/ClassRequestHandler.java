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

import co.com.soinsoftware.schoolmanagement.bll.ClassBLL;
import co.com.soinsoftware.schoolmanagement.bll.FinalNoteBLL;
import co.com.soinsoftware.schoolmanagement.bll.NoteDefinitionBLL;
import co.com.soinsoftware.schoolmanagement.bll.NoteValueBLL;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.NoteDefinitionBO;
import co.com.soinsoftware.schoolmanagement.entity.NoteValueBO;
import co.com.soinsoftware.schoolmanagement.mapper.ClassMapper;
import co.com.soinsoftware.schoolmanagement.mapper.NoteDefinitionMapper;
import co.com.soinsoftware.schoolmanagement.mapper.NoteValueMapper;

/**
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 28/01/2016
 */
@Path("/schoolmanagement/class/")
public class ClassRequestHandler extends AbstractRequestHandler {

	private final ClassBLL classBLL = ClassBLL.getInstance();
	
	private final FinalNoteBLL finalNoteBLL = FinalNoteBLL.getInstance();
	
	private final NoteDefinitionBLL noteDefBLL = NoteDefinitionBLL.getInstance();
	
	private final NoteValueBLL noteValueBLL = NoteValueBLL.getInstance();

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
	
	@GET
	@Path(PATH_NOTEDEFINITION_BY_CLASS)
	@Produces(APPLICATION_JSON)
	public Set<NoteDefinitionBO> findNoteDefinitionByClass(
			@QueryParam(PARAMETER_CLASS_ID) final int idClass,
			@QueryParam(PARAMETER_PERIOD_ID) final int idPeriod) {
		final Set<NoteDefinitionBO> noteDefSet = classBLL.findNoteDefinition(
				idClass, idPeriod);
		LOGGER.info("findNoteDefinitionByClass function loads {}", noteDefSet.toString());
		return noteDefSet;
	}
	
	@POST
	@Path(PATH_SAVE_NOTEDEFINITION_BY_CLASS)
	@Produces(APPLICATION_JSON)
	public ClassBO saveNoteDefinitionsByClass(
			@FormParam(PARAMETER_OBJECT) final String jsonObject) {
		ClassBO classBO = null;
		final List<NoteDefinitionBO> noteDefList = new NoteDefinitionMapper()
				.getObjectListFromJSON(jsonObject);
		if (noteDefList != null && !noteDefList.isEmpty()) {
			for (NoteDefinitionBO noteDefinition : noteDefList) {
				this.noteDefBLL.saveRecord(noteDefinition);
				LOGGER.info(
						"saveNoteDefinitionsByClass function applied to {}",
						noteDefinition);
			}
			final int idClass = noteDefList.get(0).getIdClass();
			classBO = this.classBLL.selectByIdentifierAndPutInCache(idClass);
		}
		return classBO;
	}
	
	@POST
	@Path(PATH_SAVE_NOTEVALUE)
	@Produces(APPLICATION_JSON)
	public ClassBO saveNoteValue(
			@FormParam(PARAMETER_OBJECT) final String jsonObject) {
		ClassBO classBO = null;
		final List<NoteValueBO> noteValueList = new NoteValueMapper()
				.getObjectListFromJSON(jsonObject);
		if (noteValueList != null && !noteValueList.isEmpty()) {
			for (final NoteValueBO noteValue : noteValueList) {
				this.noteValueBLL.saveRecord(noteValue);
				LOGGER.info("saveNoteValue function applied to {}", noteValue);
			}
			final int idNoteDefinition = noteValueList.get(0)
					.getIdNoteDefinition();
			final NoteDefinitionBO noteDefinition = this.noteDefBLL
					.findByIdentifier(idNoteDefinition);
			classBO = this.classBLL
					.selectByIdentifierAndPutInCache(noteDefinition
							.getIdClass());
			LOGGER.info("Initialization of thread to calculate and save Final Note values");
			this.finalNoteBLL.doSaveProcess(classBO, noteDefinition.getPeriod()
					.getId());
		}
		return classBO;
	}
}