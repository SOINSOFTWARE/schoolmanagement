package co.com.soinsoftware.schoolmanagement.bll;

import java.util.Date;
import java.util.Set;

import co.com.soinsoftware.schoolmanagement.dao.NoteDefinitionDAO;
import co.com.soinsoftware.schoolmanagement.entity.NoteDefinitionBO;
import co.com.soinsoftware.schoolmanagement.entity.PeriodBO;
import co.com.soinsoftware.schoolmanagement.entity.YearBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bznotedefinition;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzperiod;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 26/04/2016
 */
public class NoteDefinitionBLL extends AbstractBLL implements
		IBusinessLogicLayer<NoteDefinitionBO, Bznotedefinition> {
	
	private final NoteDefinitionDAO dao;
	
	private ClassBLL classBLL;
	
	private PeriodBLL periodBLL;
	
	private static NoteDefinitionBLL instance;
	
	private NoteDefinitionBLL() {
		super();
		this.dao = new NoteDefinitionDAO();
	}
	
	public static NoteDefinitionBLL getInstance() {
		if (instance == null) {
			instance = new NoteDefinitionBLL();
			instance.classBLL = ClassBLL.getInstance();
			instance.periodBLL = PeriodBLL.getInstance();
		}
		return instance;
	}

	@Override
	public Set<NoteDefinitionBO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NoteDefinitionBO> findAll(int idSchool) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteDefinitionBO findByIdentifier(final Integer identifier) {
		final Bznotedefinition bzNoteDefinition = this.dao
				.selectByIdentifier(identifier);
		final Bzperiod bzPeriod = bzNoteDefinition.getBzperiod();
		final YearBO year = new YearBO(bzPeriod.getBzyear());
		final PeriodBO period = new PeriodBO(bzPeriod, year);
		final NoteDefinitionBO noteDefinition = new NoteDefinitionBO(
				bzNoteDefinition, period, null);
		noteDefinition.setIdClass(bzNoteDefinition.getBzclass().getId());
		return noteDefinition;
	}

	@Override
	public NoteDefinitionBO findByCode(int idSchool, String code, int identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteDefinitionBO saveRecord(final NoteDefinitionBO record) {
		if (record.getClassBO() == null) {
			record.setClassBO(this.classBLL.findByIdentifier(record.getIdClass()));
		}
		if (record.getPeriod() == null) {
			record.setPeriod(this.periodBLL.findByIdentifier(record.getIdPeriod()));
		}
		record.setUpdated(new Date());
		final Bznotedefinition bzNoteDefinition = this.buildHibernateEntity(record);
		this.dao.save(bzNoteDefinition);
		record.setId(bzNoteDefinition.getId());
		return record;
	}

	@Override
	public Set<NoteDefinitionBO> selectAndPutInCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteDefinitionBO selectByIdentifierAndPutInCache(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NoteDefinitionBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteDefinitionBO putObjectInCache(Bznotedefinition record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bznotedefinition buildHibernateEntity(final NoteDefinitionBO record) {
		Bznotedefinition bzNoteDefinition = new Bznotedefinition();
		if (record != null) {
			bzNoteDefinition.setId(record.getId());
			bzNoteDefinition.setName(record.getName());
			bzNoteDefinition.setDescription(record.getDescription());
			bzNoteDefinition.setValue(record.getValue());
			bzNoteDefinition.setBzclass(this.classBLL
					.buildHibernateEntity(record.getClassBO()));
			bzNoteDefinition.setBzperiod(this.periodBLL
					.buildHibernateEntity(record.getPeriod()));
			bzNoteDefinition.setCreation(record.getCreation());
			bzNoteDefinition.setUpdated(record.getUpdated());
			bzNoteDefinition.setEnabled(record.isEnabled());
		}
		return bzNoteDefinition;
	}

	@Override
	protected Set<?> getObjectsFromCache() {
		// TODO Auto-generated method stub
		return null;
	}

}
