/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.ProjectTypeDAO;
import hp.bootmgr.vo.ProjectType;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ProjectTypeDAOImpl implements ProjectTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ProjectType> getAll() {
		return sessionFactory.getCurrentSession()
				.createCriteria(ProjectType.class).list();
	}

	@Override
	public ProjectType getById(Integer key) {
		return (ProjectType) sessionFactory.getCurrentSession().get(
				ProjectType.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(ProjectType projectType) {
		sessionFactory.getCurrentSession().delete(projectType);
	}

	@Override
	public void update(ProjectType projectType) {
		sessionFactory.getCurrentSession().update(projectType);
	}

	@Override
	public void save(ProjectType t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
