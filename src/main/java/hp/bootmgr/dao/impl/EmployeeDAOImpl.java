/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hp.bootmgr.dao.EmployeeDAO;
import hp.bootmgr.vo.Employee;

@Repository
@SuppressWarnings("unchecked")
public class EmployeeDAOImpl implements EmployeeDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Employee> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(Employee.class).list();
	}

	@Override
	public Employee getById(Integer key) {
		return (Employee) sessionFactory.getCurrentSession().get(Employee.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(Employee object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public void update(Employee object) {
		sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void save(Employee object) {
		sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void merge(Employee employee) {
		sessionFactory.getCurrentSession().merge(employee);
	}

}
