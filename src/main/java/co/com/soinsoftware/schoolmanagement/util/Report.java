package co.com.soinsoftware.schoolmanagement.util;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Set;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalImageAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

	private final JasperReportBuilder report;

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
		report = DynamicReports.report();
	}

	public boolean generate() {
		boolean generated = false;
		try {
			this.buildTitleSection();
			this.buildPageHeaderSection();
			this.buildDetailSection();
			this.buildColumnFooter();
			report.toPdf(new FileOutputStream(this.getReportFile()));
			generated = true;
		} catch (DRException | FileNotFoundException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return generated;
	}

	private void buildTitleSection() throws FileNotFoundException {
		final File imgFile = new File(this.school.getPhoto());
		final InputStream image = new FileInputStream(imgFile);
		final String schoolName = this.school.getName();
		final StringBuilder schoolId = new StringBuilder("NIT: ");
		schoolId.append(this.school.getNit());
		schoolId.append(" ");
		schoolId.append("DANE: ");
		schoolId.append(this.school.getDane());
		final StyleBuilder styleFont15 = this
				.getNewStyle()
				.setFontSize(15)
				.setLeftPadding(5)
				.bold()
				.setTextAlignment(HorizontalTextAlignment.LEFT,
						VerticalTextAlignment.BOTTOM);
		final StyleBuilder styleFont6 = this
				.getNewStyle()
				.setFontSize(6)
				.setLeftPadding(5)
				.bold()
				.setTextAlignment(HorizontalTextAlignment.LEFT,
						VerticalTextAlignment.TOP);
		this.report.title(
				cmp.horizontalList().add(
						cmp.image(image).setStyle(
								this.getNewStyle().setImageAlignment(
										HorizontalImageAlignment.RIGHT,
										VerticalImageAlignment.BOTTOM)),
						cmp.verticalList().add(
								this.getTextField(schoolName, styleFont15),
								this.getTextField(schoolId.toString(),
										styleFont6).setFixedHeight(45))),
				cmp.verticalGap(30));
	}

	private void buildPageHeaderSection() {
		final StyleBuilder styleFont8 = this.getNewStyle().setFontSize(8)
				.setBackgroundColor(Color.WHITE)
				.setBorder(DynamicReports.stl.pen1Point()).setLeftPadding(5);
		final StyleBuilder styleFont12 = this.getNewStyle().setFontSize(12)
				.bold().setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(DynamicReports.stl.pen1Point()).setLeftPadding(5);
		this.report.pageHeader(cmp.verticalList().add(
				this.getTextField(this.getPageHeaderTitle(), styleFont12),
				cmp.horizontalList(this.getTextField("Periodo:", styleFont8)
						.setFixedDimension(100, 15), this.getTextField(
						this.getPeriodInformation(), styleFont8)),
				cmp.horizontalList(
						this.getTextField("Núm. Documento:", styleFont8)
								.setFixedDimension(100, 15), this.getTextField(
								this.student.getDocumentNumber(), styleFont8)),
				cmp.horizontalList(this.getTextField("Acudiente:", styleFont8)
						.setFixedDimension(100, 15), this.getTextField(
						this.getFullName(this.student.getGuardian1()),
						styleFont8)), cmp.verticalGap(20)));
	}

	private void buildDetailSection() {
		final StyleBuilder styleRowTitle = this.getNewStyle().bold()
				.setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(DynamicReports.stl.pen1Point()).setLeftPadding(5);
		final StyleBuilder styleRowDetail = this
				.getNewStyle()
				.setBorder(DynamicReports.stl.pen1Point())
				.setFontSize(8)
				.setLeftPadding(5)
				.setTextAlignment(HorizontalTextAlignment.JUSTIFIED,
						VerticalTextAlignment.TOP);
		final StyleBuilder styleRowBold = this
				.getNewStyle()
				.bold()
				.setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(DynamicReports.stl.pen1Point())
				.setLeftPadding(5)
				.setTextAlignment(HorizontalTextAlignment.JUSTIFIED,
						VerticalTextAlignment.TOP);
		final TextColumnBuilder<BigDecimal> colNoteValue = col.column(
				"Calificación", "finalNote.value", type.bigDecimalType());
		final TextColumnBuilder<String> colClass = col.column("Asignatura",
				"name", type.stringType());
		final TextColumnBuilder<String> colQualitative = col.column(
				"Cualitativa", "qualitativeNote", type.stringType());
		final TextColumnBuilder<String> colAchievement = col.column("Logros",
				"achievements", type.stringType());
		AggregationSubtotalBuilder<Number> sumNoteValue = sbt.avg(colNoteValue)
				.setStyle(styleRowBold);
		AggregationSubtotalBuilder<String> sumClass = sbt.text(
				"Promedio del estudiante", colClass).setStyle(styleRowBold);
		AggregationSubtotalBuilder<String> sumQualitative = sbt.text("",
				colQualitative).setStyle(styleRowBold);
		AggregationSubtotalBuilder<String> sumAchievement = sbt.text("",
				colAchievement).setStyle(styleRowBold);
		this.report
				.columns(colClass.setFixedWidth(125),
						colNoteValue.setFixedWidth(65),
						colQualitative.setFixedWidth(100), colAchievement)
				.setColumnTitleStyle(styleRowTitle)
				.setColumnStyle(styleRowDetail)
				.subtotalsAtSummary(sumNoteValue).subtotalsAtSummary(sumClass)
				.subtotalsAtSummary(sumQualitative)
				.subtotalsAtSummary(sumAchievement);
		this.report.setDataSource(this.createDataSource());

	}

	private void buildColumnFooter() {
		final StyleBuilder styleFont12 = this
				.getNewStyle()
				.setFontSize(12)
				.bold()
				.setLeftPadding(5)
				.setTextAlignment(HorizontalTextAlignment.LEFT,
						VerticalTextAlignment.TOP);
		final UserBO teacher = this.classRoom.getTeacher();
		this.report.columnFooter(cmp.verticalList().add(
				cmp.line().setPen(DynamicReports.stl.pen1Point())
						.setFixedWidth(200),
				this.getTextField(this.getFullName(teacher), styleFont12),
				this.getTextField("Director(a) de grupo", styleFont12),
				cmp.verticalGap(40)));
	}

	private TextFieldBuilder<String> getTextField(final String text,
			final StyleBuilder style) {
		return cmp.text(text).setStyle(style);
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

	private StyleBuilder getNewStyle() {
		return new StyleBuilders().style();
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