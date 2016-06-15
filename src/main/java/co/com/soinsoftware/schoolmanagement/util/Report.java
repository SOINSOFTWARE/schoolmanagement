package co.com.soinsoftware.schoolmanagement.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.FinalNoteBO;
import co.com.soinsoftware.schoolmanagement.entity.NoteDefinitionBO;
import co.com.soinsoftware.schoolmanagement.entity.NoteValueBO;
import co.com.soinsoftware.schoolmanagement.entity.NoteValueConfigurationBO;
import co.com.soinsoftware.schoolmanagement.entity.PeriodBO;
import co.com.soinsoftware.schoolmanagement.entity.SchoolBO;
import co.com.soinsoftware.schoolmanagement.entity.UserBO;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 11/05/2016
 */
public class Report {

	private static final Logger LOGGER = LoggerFactory.getLogger(Report.class);

	private static final String REPORT_NAME = "/school.jasper";

	private static final String PARAM_SCHOOL_IMAGE = "SchoolImg";
	private static final String PARAM_COMPANY_NAME = "CompanyName";
	private static final String PARAM_COMPANY_ID = "CompanyId";
	private static final String PARAM_CLASS_AND_USER = "ClassAndUser";
	private static final String PARAM_PERIOD = "Period";
	private static final String PARAM_DOCUMENT_NUMBER = "DocumentNumber";
	private static final String PARAM_GUARDIAN_NAME = "GuardianName";
	private static final String PARAM_GROUP_DIRECTOR = "GroupDirector";

	private final ClassRoomBO classRoom;

	private final PeriodBO period;

	private final SchoolBO school;

	private final UserBO student;

	private final Set<FinalNoteBO> finalNoteSet;

	public Report(final SchoolBO school, final PeriodBO period,
			final ClassRoomBO classRoom, final UserBO student,
			final Set<FinalNoteBO> finalNoteSet) {
		super();
		this.school = school;
		this.period = period;
		this.classRoom = classRoom;
		this.student = student;
		this.finalNoteSet = finalNoteSet;
	}

	public boolean generate() {
		boolean generated = false;
		try {
			System.out.println("Loading jasper file");
			final JasperReport jasReport = this.loadJasperReport();
			final Map<String, Object> parameters = this.createParameters();
			final JRDataSource dataSource = this.createDataSource();
			System.out.println("Filling report");
			final JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasReport, parameters, dataSource);
			System.out.println("Starting show");
			JasperExportManager.exportReportToPdfFile(jasperPrint, this
					.getReportFile().getAbsolutePath());
			generated = true;
		} catch (JRException | FileNotFoundException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return generated;
	}

	private JasperReport loadJasperReport() throws JRException {
		final InputStream resourceIS = this.getClass().getResourceAsStream(
				REPORT_NAME);
		return (JasperReport) JRLoader.loadObject(resourceIS);
	}

	private Map<String, Object> createParameters() throws FileNotFoundException {
		final File imgFile = new File(this.school.getPhoto());
		final InputStream image = new FileInputStream(imgFile);
		final StringBuilder schoolId = new StringBuilder("NIT: ");
		schoolId.append(this.school.getNit());
		schoolId.append(" ");
		schoolId.append("DANE: ");
		schoolId.append(this.school.getDane());
		final UserBO teacher = this.classRoom.getTeacher();
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put(PARAM_SCHOOL_IMAGE, image);
		parameters.put(PARAM_COMPANY_NAME, this.school.getName());
		parameters.put(PARAM_COMPANY_ID, schoolId.toString());
		parameters.put(PARAM_CLASS_AND_USER, this.getPageHeaderTitle());
		parameters.put(PARAM_PERIOD, this.getPeriodInformation());
		parameters.put(PARAM_DOCUMENT_NUMBER, this.student.getDocumentNumber());
		parameters.put(PARAM_GUARDIAN_NAME,
				this.getFullName(this.student.getGuardian1()));
		parameters.put(PARAM_GROUP_DIRECTOR, this.getFullName(teacher));
		return parameters;
	}

	private String getPageHeaderTitle() {
		final StringBuilder classRoomAndUser = new StringBuilder(
				this.classRoom.getName());
		classRoomAndUser.append(" - ");
		classRoomAndUser.append(this.student.getName());
		classRoomAndUser.append(" ");
		classRoomAndUser.append(this.student.getLastName());
		return classRoomAndUser.toString();
	}

	private String getPeriodInformation() {
		final StringBuilder periodInfo = new StringBuilder("[");
		periodInfo.append(this.period.getYear().getName());
		periodInfo.append("] ");
		periodInfo.append(this.period.getName());
		return periodInfo.toString();
	}

	private String getFullName(final UserBO user) {
		final StringBuilder guardianInformation = new StringBuilder();
		guardianInformation.append(user.getName());
		guardianInformation.append(" ");
		guardianInformation.append(user.getLastName());
		return guardianInformation.toString();
	}

	private JRDataSource createDataSource() {
		this.putFinalNoteIntoClass();
		return new JRBeanCollectionDataSource(this.classRoom.getClassSet());
	}

	private void putFinalNoteIntoClass() {
		if (this.classRoom.getClassSet() != null) {
			for (final ClassBO classBO : this.classRoom.getClassSet()) {
				for (final FinalNoteBO finalNote : this.finalNoteSet) {
					if (this.student.getId().equals(finalNote.getIdStudent())
							&& classBO.getId().equals(finalNote.getIdClass())) {
						classBO.setFinalNote(finalNote);
						classBO.setQualitativeNote(this
								.getQualitativeValue(finalNote.getValue()));
						classBO.setAchievements(this.getAchievements(classBO));
						break;
					}
				}
				if (classBO.getFinalNote() == null) {
					final FinalNoteBO finalNote = new FinalNoteBO();
					finalNote.setValue(new BigDecimal(0));
					classBO.setFinalNote(finalNote);
					classBO.setQualitativeNote(this
							.getQualitativeValue(finalNote.getValue()));
				}
			}
		}
	}

	private String getQualitativeValue(final BigDecimal value) {
		String quantitativeValue = "";
		if (this.school.getNote() != null
				&& this.school.getNote().getNoteValueSet() != null) {
			for (final NoteValueConfigurationBO conf : this.school.getNote()
					.getNoteValueSet()) {
				final BigDecimal rangeStart = conf.getRangeStart();
				final BigDecimal rangeEnd = conf.getRangeEnd();
				if (rangeStart.compareTo(value) <= 0
						&& rangeEnd.compareTo(value) >= 0) {
					quantitativeValue = conf.getName();
					break;
				}
			}
		}
		return quantitativeValue;
	}

	private String getAchievements(final ClassBO classBO) {
		final StringBuilder achievementBuilder = new StringBuilder();
		if (classBO.getNoteDefinitionSet() != null) {
			for (final NoteDefinitionBO noteDef : classBO
					.getNoteDefinitionSet()) {
				if (noteDef.getPeriod().equals(this.period)
						&& noteDef.getNoteValueSet() != null) {
					for (final NoteValueBO noteValue : noteDef
							.getNoteValueSet()) {
						if (noteValue.getIdStudent() == student.getId()) {
							final String qualitative = this
									.getQualitativeValue(noteValue.getValue());
							achievementBuilder.append(qualitative);
							achievementBuilder.append(" en ");
							achievementBuilder.append(noteDef.getDescription()
									.toLowerCase());
							achievementBuilder.append("\n");
						}
					}
				}
			}
		}
		return achievementBuilder.toString();
	}

	private File getReportFile() {
		final File reportDir = this.getReportFolderDir();
		final String pdfFileName = this.student.getDocumentNumber() + ".pdf";
		return new File(reportDir, pdfFileName);
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