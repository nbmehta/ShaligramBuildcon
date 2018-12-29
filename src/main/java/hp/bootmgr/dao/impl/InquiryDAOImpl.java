/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hp.bootmgr.dao.InquiryDAO;
import hp.bootmgr.vo.Inquiry;

@Repository
@SuppressWarnings("unchecked")
public class InquiryDAOImpl implements InquiryDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Inquiry> getAll() {
		
		return sessionFactory.getCurrentSession().createCriteria(Inquiry.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public Inquiry getById(Integer key) {
		// TODO Auto-generated method stub
		return (Inquiry) sessionFactory.getCurrentSession().get(Inquiry.class, key);

	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
		
	}

	@Override
	public void delete(Inquiry object) {
		sessionFactory.getCurrentSession().delete(object);
		
	}

	@Override
	public void update(Inquiry object) {
		sessionFactory.getCurrentSession().update(object);
		
	}

	@Override
	public void save(Inquiry object) {
		sessionFactory.getCurrentSession().save(object);
		
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
