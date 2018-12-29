/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.State;

public interface StateDAO extends GenericDAO<State, Integer> {
	public Session getSession();
}
