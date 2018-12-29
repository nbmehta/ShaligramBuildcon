/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services.impl;

import hp.bootmgr.dao.StateDAO;
import hp.bootmgr.services.StateManagementService;
import hp.bootmgr.vo.State;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("stateManagementService")
public class StateManagementServiceImpl implements StateManagementService {
	
	@Autowired
	private StateDAO stateDAO;

	@Override
	public State getById(int id) {
		try {
			return stateDAO.getById(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<State> getAll() {
		try {
			return stateDAO.getAll();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(int id) {
		try {
			stateDAO.deleteById(id);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(State state) {
		try {
			stateDAO.delete(state);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(State state) {
		try {
			stateDAO.update(state);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean save(State state) {
		try {
			stateDAO.save(state);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isAlreadyExist(String stateName) {
		try{
			return stateDAO.getSession().createCriteria(State.class).add(Restrictions.eq("stateName", stateName)).list().size() > 0;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public State getStateByName(String name) {
		try {
			List ret = stateDAO.getSession().createCriteria(State.class).add(Restrictions.eq("stateName", name)).list();
			return ret.size() > 0 ? (State) ret.get(0) : null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
