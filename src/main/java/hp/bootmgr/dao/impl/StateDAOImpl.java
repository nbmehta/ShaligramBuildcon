/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.StateDAO;
import hp.bootmgr.vo.State;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StateDAOImpl implements StateDAO {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<State> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(State.class).list();
	}

	@Override
	public State getById(Integer key) {
		return (State) sessionFactory.getCurrentSession().get(State.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void update(State object) {
		sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void save(State object) {
		sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void delete(State object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
