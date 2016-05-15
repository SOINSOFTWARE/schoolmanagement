package co.com.soinsoftware.schoolmanagement.util;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.FinalNoteBO;
import co.com.soinsoftware.schoolmanagement.entity.PeriodBO;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 11/05/2016
 */
public class ReportThread extends Thread {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReportThread.class);

	private final SchoolBO school;

	private final ClassRoomBO classRoom;

	private final PeriodBO period;

	private final Set<FinalNoteBO> finalNoteSet;

	public ReportThread(final SchoolBO school, final ClassRoomBO classRoom,
			final PeriodBO period, final Set<FinalNoteBO> finalNoteSet) {
		super();
		this.school = school;
		this.classRoom = classRoom;
		this.period = period;
		this.finalNoteSet = finalNoteSet;
	}

	@Override
	public void run() {
		if (this.classRoom.getStudentSet() != null) {
			for (final UserBO student : this.classRoom.getStudentSet()) {
				LOGGER.info("Starting report generation for student {} ",
						student);
				final Report report = new Report(school, period, classRoom,
						student, finalNoteSet);
				final boolean generated = report.generate();
				if (!generated) {
					LOGGER.error(
							"Report for student {} was not generated, review the log",
							student);
				} else {
					LOGGER.info("Report generated correctly");
				}
			}
		}
	}
}