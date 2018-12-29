/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services;

import java.util.List;

import hp.bootmgr.vo.State;

public interface StateManagementService {
	public State getById(int id);
	public List<State> getAll();
	public boolean deleteById(int id);
	public boolean delete(State state);
	public boolean update(State state);
	public boolean save(State state);
	public boolean isAlreadyExist(String stateName);
	public State getStateByName(String name);
}
