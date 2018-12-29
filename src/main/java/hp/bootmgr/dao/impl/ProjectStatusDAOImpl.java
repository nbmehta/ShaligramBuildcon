/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.ProjectStatusDAO;
import hp.bootmgr.vo.ProjectStatus;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class ProjectStatusDAOImpl implements ProjectStatusDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ProjectStatus> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(ProjectStatus.class).list();
	}

	@Override
	public ProjectStatus getById(Integer key) {
		return (ProjectStatus) sessionFactory.getCurrentSession().get(ProjectStatus.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(ProjectStatus projectStatus) {
		sessionFactory.getCurrentSession().delete(projectStatus);
		
	}

	@Override
	public void update(ProjectStatus projectStatus) {
		sessionFactory.getCurrentSession().update(projectStatus);
		
	}

	@Override
	public void save(ProjectStatus t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public Session getSession() {
	return sessionFactory.getCurrentSession();
	}

}
