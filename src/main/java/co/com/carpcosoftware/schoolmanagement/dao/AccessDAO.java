package co.com.carpcosoftware.schoolmanagement.dao;

import java.util.Set;

import co.com.carpcosoftware.schoolmanagement.entity.AccessBO;

/**
 * Access data access object
 * 
 * @author Carlos Andres Rodriguez
 * @version 1.0
 * @since 31/03/2015
 */
public class AccessDAO extends AbstractDAO implements IDataAccesable<AccessBO> {

	@Override
	public Set<AccessBO> select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccessBO selectByIdentifier(Integer identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccessBO selectByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(AccessBO record) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getSelectStatementWithoutWhere() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSelectStatementByIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSelectStatementByCode() {
		// TODO Auto-generated method stub
		return null;
	}
}