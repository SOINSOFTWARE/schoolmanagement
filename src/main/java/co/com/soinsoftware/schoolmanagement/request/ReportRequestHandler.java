package co.com.soinsoftware.schoolmanagement.request;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import co.com.soinsoftware.schoolmanagement.bll.ClassRoomBLL;
import co.com.soinsoftware.schoolmanagement.bll.FinalNoteBLL;
import co.com.soinsoftware.schoolmanagement.bll.PeriodBLL;
import co.com.soinsoftware.schoolmanagement.bll.SchoolBLL;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.FinalNoteBO;
import co.com.soinsoftware.schoolmanagement.entity.PeriodBO;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.util.ReportThread;
import co.com.soinsoftware.schoolmanagement.util.ServiceLocator;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 11/05/2016
 */
@Path("/schoolmanagement/report/")
public class ReportRequestHandler extends AbstractRequestHandler {

	@Autowired
	private final ClassRoomBLL classRoomBLL = ServiceLocator
			.getBean(ClassRoomBLL.class);

	@Autowired
	private final PeriodBLL periodBLL = ServiceLocator.getBean(PeriodBLL.class);

	@Autowired
	private final SchoolBLL schoolBLL = ServiceLocator.getBean(SchoolBLL.class);

	@Autowired
	private final FinalNoteBLL finalNoteBLL = ServiceLocator
			.getBean(FinalNoteBLL.class);

	@GET
	@Path(PATH_BY)
	public void generateReports(
			@QueryParam(PARAMETER_SCHOOL_ID) final int idSchool,
			@QueryParam(PARAMETER_CLASSROOM_ID) final int idClassRoom,
			@QueryParam(PARAMETER_PERIOD_ID) final int idPeriod) {
		final SchoolBO school = this.schoolBLL.findByIdentifier(idSchool);
		final ClassRoomBO classRoom = this.classRoomBLL
				.findByIdentifier(idClassRoom);
		final PeriodBO period = this.periodBLL.findByIdentifier(idPeriod);
		LOGGER.info(
				"generateReports function loads school={}, classroom={}, period={}",
				school, classRoom, period);
		if (school != null && classRoom != null && period != null) {
			final Set<FinalNoteBO> finalNoteSet = this.finalNoteBLL
					.findAllByClassRoom(classRoom, period);
			final ReportThread report = new ReportThread(school, classRoom,
					period, finalNoteSet);
			report.start();
		}
	}

}