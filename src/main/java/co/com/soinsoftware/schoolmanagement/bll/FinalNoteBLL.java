package co.com.soinsoftware.schoolmanagement.bll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import co.com.soinsoftware.schoolmanagement.dao.FinalNoteDAO;
import co.com.soinsoftware.schoolmanagement.entity.ClassBO;
import co.com.soinsoftware.schoolmanagement.entity.ClassRoomBO;
import co.com.soinsoftware.schoolmanagement.entity.FinalNoteBO;
import co.com.soinsoftware.schoolmanagement.entity.NoteDefinitionBO;
import co.com.soinsoftware.schoolmanagement.entity.NoteValueBO;
import co.com.soinsoftware.schoolmanagement.entity.PeriodBO;
import co.com.soinsoftware.schoolmanagement.hibernate.Bzfinalnote;
import co.com.soinsoftware.schoolmanagement.hibernate.BzfinalnoteId;

/**
 * @author Carlos Rodriguez
 * @version 1.0
 * @since 13/05/2016
 */
public class FinalNoteBLL extends AbstractBLL implements
		IBusinessLogicLayer<FinalNoteBO, Bzfinalnote> {

	private final FinalNoteDAO dao;
	
	private static FinalNoteBLL instance;
	
	private FinalNoteBLL() {
		super();
		this.dao = new FinalNoteDAO();
	}
	
	public static FinalNoteBLL getInstance() {
		if (instance == null) {
			instance = new FinalNoteBLL();
		}
		return instance;
	}

	@Override
	public Set<FinalNoteBO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<FinalNoteBO> findAll(final int idSchool) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinalNoteBO findByIdentifier(final Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinalNoteBO findByCode(final int idSchool, final String code,
			final int identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinalNoteBO saveRecord(final FinalNoteBO record) {
		record.setUpdated(new Date());
		final Bzfinalnote bzFinalNote = this.buildHibernateEntity(record);
		this.dao.save(bzFinalNote);
		return record;
	}

	@Override
	public Set<FinalNoteBO> selectAndPutInCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinalNoteBO selectByIdentifierAndPutInCache(final Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<FinalNoteBO> createEntityBOSetUsingHibernatEntities(
			final Set<?> hibernateEntitySet) {
		Set<FinalNoteBO> finalNoteSet = null;
		if (hibernateEntitySet != null && hibernateEntitySet.size() > 0) {
			finalNoteSet = new HashSet<>();
			for (final Object bzFinalNote : hibernateEntitySet) {
				if (bzFinalNote instanceof Bzfinalnote) {
					finalNoteSet
							.add(new FinalNoteBO((Bzfinalnote) bzFinalNote));
				}
			}
		}
		return finalNoteSet;
	}

	@Override
	public FinalNoteBO putObjectInCache(final Bzfinalnote record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bzfinalnote buildHibernateEntity(final FinalNoteBO record) {
		final Bzfinalnote bzFinalNote = new Bzfinalnote();
		if (record != null) {
			final BzfinalnoteId id = new BzfinalnoteId(record.getIdClass(),
					record.getIdStudent(), record.getIdPeriod());
			bzFinalNote.setId(id);
			bzFinalNote.setValue(record.getValue());
			bzFinalNote.setCreation(record.getCreation());
			bzFinalNote.setUpdated(record.getUpdated());
			bzFinalNote.setEnabled(record.isEnabled());
		}
		return bzFinalNote;
	}

	@Override
	protected Set<?> getObjectsFromCache() {
		// TODO Auto-generated method stub
		return null;
	}

	public void doSaveProcess(final ClassBO classBO, final int idPeriod) {
		final Thread thread = new Thread() {
			@Override
			public void run() {
				saveFinalNote(classBO, idPeriod);
			}
		};
		thread.start();
	}

	private void saveFinalNote(final ClassBO classBO, final int idPeriod) {
		final Map<Integer, BigDecimal> studentNoteMap = this.buildFinalNoteMap(
				classBO, idPeriod);
		for (final Integer key : studentNoteMap.keySet()) {
			final BigDecimal value = studentNoteMap.get(key);
			final FinalNoteBO finalNote = new FinalNoteBO(key, classBO.getId(),
					idPeriod, value, new Date(), new Date(), true);
			this.saveRecord(finalNote);
		}
	}

	private Map<Integer, BigDecimal> buildFinalNoteMap(final ClassBO classBO,
			final int idPeriod) {
		final Map<Integer, BigDecimal> studentNoteMap = new HashMap<>();
		if (classBO.getNoteDefinitionSet() != null) {
			for (final NoteDefinitionBO noteDef : classBO
					.getNoteDefinitionSet()) {
				if (noteDef.getPeriod().getId() == idPeriod
						&& noteDef.getNoteValueSet() != null) {
					final Set<NoteValueBO> noteValueList = noteDef
							.getNoteValueSet();
					for (final NoteValueBO noteValue : noteValueList) {
						final Integer key = noteValue.getIdStudent();
						final BigDecimal prevValue = studentNoteMap
								.containsKey(key) ? studentNoteMap.get(key)
								: new BigDecimal(0);
						final BigDecimal percentage = new BigDecimal(
								(double) noteDef.getValue() / 100);
						final BigDecimal value = prevValue.add(noteValue
								.getValue().multiply(percentage));
						studentNoteMap.put(key, value);
					}
				}
			}
		}
		return studentNoteMap;
	}

	public Set<FinalNoteBO> findAllByClassRoom(final ClassRoomBO classRoom,
			final PeriodBO period) {
		final Set<FinalNoteBO> finalNoteSet = new HashSet<>();
		if (classRoom.getClassSet() != null) {
			for (final ClassBO classBO : classRoom.getClassSet()) {
				final Set<FinalNoteBO> finalNotebyClassSet = this.findAllByClassAndPeriod(
						classBO.getId(), period.getId());
				if (finalNotebyClassSet != null) {
					finalNoteSet.addAll(finalNotebyClassSet);
				}
			}
		}
		return finalNoteSet;
	}

	private Set<FinalNoteBO> findAllByClassAndPeriod(final Integer idClass,
			final Integer idPeriod) {
		final Set<Bzfinalnote> bzFinalNoteSet = this.dao
				.selectByClassAndPeriod(idClass, idPeriod);
		final Set<FinalNoteBO> finalNoteSet = this
				.createEntityBOSetUsingHibernatEntities(bzFinalNoteSet);
		return finalNoteSet;
	}
}