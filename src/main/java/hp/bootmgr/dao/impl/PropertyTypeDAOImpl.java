/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.PropertyTypeDAO;
import hp.bootmgr.vo.PropertyType;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyTypeDAOImpl implements PropertyTypeDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<PropertyType> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(PropertyType.class).list();
	}

	@Override
	public void update(PropertyType propertyType) {
		sessionFactory.getCurrentSession().update(propertyType);
	}

	@Override
	public PropertyType getById(Integer key) {
		return (PropertyType) sessionFactory.getCurrentSession().get(PropertyType.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void save(PropertyType t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void delete(PropertyType object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
