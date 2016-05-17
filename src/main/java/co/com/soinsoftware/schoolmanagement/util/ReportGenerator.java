package co.com.soinsoftware.schoolmanagement.util;

import java.io.File;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipUtil;

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
public class ReportGenerator {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReportGenerator.class);

	private final SchoolBO school;

	private final ClassRoomBO classRoom;

	private final PeriodBO period;

	private final Set<FinalNoteBO> finalNoteSet;

	public ReportGenerator(final SchoolBO school, final ClassRoomBO classRoom,
			final PeriodBO period, final Set<FinalNoteBO> finalNoteSet) {
		super();
		this.school = school;
		this.classRoom = classRoom;
		this.period = period;
		this.finalNoteSet = finalNoteSet;
	}

	public File generate() {
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
			final File zipFile = this.getZipFile();
			ZipUtil.pack(this.getReportFolderDir(), zipFile);
			return zipFile;
		}
		return null;
	}

	private File getReportFolderDir() {
		final ServerProperties prop = ServerProperties.getInstance();
		final String reportDir = prop.getReportFolder();
		final File reportFolderDir = new File(reportDir);
		final String subFolder = this.buildSubFolderDirString();
		final File subFolderDir = new File(reportFolderDir, subFolder);
		if (!subFolderDir.exists()) {
			subFolderDir.mkdirs();
		}
		return subFolderDir;
	}

	private File getZipFile() {
		final ServerProperties prop = ServerProperties.getInstance();
		final String reportDir = prop.getReportFolder();
		final File reportFolderDir = new File(reportDir);
		final String zipFileStr = this.buildSubFolderDirString() + ".zip";
		final File zipFile = new File(reportFolderDir, zipFileStr);
		return zipFile;
	}

	private String buildSubFolderDirString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(this.school.getId());
		builder.append(File.separator);
		builder.append(this.period.getYear().getName());
		builder.append(File.separator);
		builder.append(this.period.getCode());
		builder.append(File.separator);
		builder.append(this.classRoom.getName());
		return builder.toString();
	}
}