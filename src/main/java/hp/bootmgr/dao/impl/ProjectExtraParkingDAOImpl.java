/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.ProjectExtraParkingDAO;
import hp.bootmgr.vo.ProjectExtraParking;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class ProjectExtraParkingDAOImpl implements ProjectExtraParkingDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ProjectExtraParking> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(ProjectExtraParking.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public ProjectExtraParking getById(Integer key) {
		
		return (ProjectExtraParking) sessionFactory.getCurrentSession().get(ProjectExtraParking.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
		}

	@Override
	public void delete(ProjectExtraParking object) {
		sessionFactory.getCurrentSession().delete(object);
		
	}

	@Override
	public void update(ProjectExtraParking object) {
		sessionFactory.getCurrentSession().update(object);
		
	}

	@Override
	public void save(ProjectExtraParking object) {
		sessionFactory.getCurrentSession().save(object);
		
	}

}
