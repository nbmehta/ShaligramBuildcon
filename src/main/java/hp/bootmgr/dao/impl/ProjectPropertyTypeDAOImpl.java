/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao.impl;

import java.util.List;

import hp.bootmgr.dao.ProjectPropertyTypeDAO;
import hp.bootmgr.vo.ProjectPropertyType;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class ProjectPropertyTypeDAOImpl implements ProjectPropertyTypeDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ProjectPropertyType> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(ProjectPropertyType.class).list();
		
	}

	@Override
	public ProjectPropertyType getById(Integer key) {
		return(ProjectPropertyType) sessionFactory.getCurrentSession().get(ProjectPropertyType.class, key);
		
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
		
	}

	@Override
	public void delete(ProjectPropertyType object) {
		sessionFactory.getCurrentSession().delete(object);
		
	}

	@Override
	public void update(ProjectPropertyType object) {
	sessionFactory.getCurrentSession().update(object);
		
	}

	@Override
	public void save(ProjectPropertyType object) {
	sessionFactory.getCurrentSession().save(object);
		
	}

}
