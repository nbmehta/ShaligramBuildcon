package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.EmployeeRoleDAO;
import hp.bootmgr.vo.EmployeeRole;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRoleDAOImpl implements EmployeeRoleDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeRole> getAll() {

		return sessionFactory.getCurrentSession().createCriteria(EmployeeRole.class).list();
	}

	@Override
	public EmployeeRole getById(Integer key) {
		
		return (EmployeeRole) sessionFactory.getCurrentSession().get(EmployeeRole.class, key);
	}

	@Override
	public void deleteById(Integer key) {
	
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(EmployeeRole object) {
		
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public void update(EmployeeRole employeeRole) {
	
		sessionFactory.getCurrentSession().update(employeeRole);
	}

	@Override
	public void save(EmployeeRole e) {
		
		sessionFactory.getCurrentSession().save(e);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	

}
