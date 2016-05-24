package co.com.soinsoftware.schoolmanagement.bll;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import co.com.soinsoftware.schoolmanagement.dao.NoteValueDAO;
import co.com.soinsoftware.schoolmanagement.entity.NoteValueBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bznotevalue;
import co.com.soinsoftware.schoolmanagement.hibernate.BznotevalueId;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 27/04/2016
 */
public class NoteValueBLL extends AbstractBLL implements
		IBusinessLogicLayer<NoteValueBO, Bznotevalue> {

	private final NoteValueDAO dao;
	
	private static NoteValueBLL instance;
	
	private NoteValueBLL() {
		super();
		this.dao = new NoteValueDAO();
	}
	
	public static NoteValueBLL getInstance() {
		if (instance == null) {
			instance = new NoteValueBLL();
		}
		return instance;
	}

	@Override
	public Set<NoteValueBO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NoteValueBO> findAll(int idSchool) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteValueBO findByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteValueBO findByCode(int idSchool, String code, int identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteValueBO saveRecord(NoteValueBO record) {
		record.setUpdated(new Date());
		final Bznotevalue bzNoteValue = this.buildHibernateEntity(record);
		this.dao.save(bzNoteValue);
		return record;
	}

	@Override
	public Set<NoteValueBO> selectAndPutInCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoteValueBO selectByIdentifierAndPutInCache(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<NoteValueBO> createEntityBOSetUsingHibernatEntities(
			Set<?> hibernateEntitySet) {
		Set<NoteValueBO> noteValueSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			noteValueSet = new HashSet<>();
			for (Object bzNoteValue : hibernateEntitySet) {
				if (bzNoteValue instanceof Bznotevalue) {
					noteValueSet
							.add(new NoteValueBO((Bznotevalue) bzNoteValue));
				}
			}
		}
		return noteValueSet;
	}

	@Override
	public NoteValueBO putObjectInCache(Bznotevalue record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bznotevalue buildHibernateEntity(NoteValueBO record) {
		final Bznotevalue bzNoteValue = new Bznotevalue();
		if (record != null) {
			final BznotevalueId id = new BznotevalueId(
					record.getIdNoteDefinition(), record.getIdStudent());
			bzNoteValue.setId(id);
			bzNoteValue.setValue(record.getValue());
			bzNoteValue.setCreation(record.getCreation());
			bzNoteValue.setUpdated(record.getUpdated());
			bzNoteValue.setEnabled(record.isEnabled());
		}
		return bzNoteValue;
	}

	@Override
	protected Set<?> getObjectsFromCache() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<NoteValueBO> findAllByIdNoteDefinition(
			final Integer idNoteDefinition) {
		final Set<Bznotevalue> bzNoteValueSet = this.dao
				.selectByIdNoteDefinition(idNoteDefinition);
		final Set<NoteValueBO> noteValueSet = this
				.createEntityBOSetUsingHibernatEntities(bzNoteValueSet);
		return noteValueSet;
	}
}
