/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services.impl;

import hp.bootmgr.dao.EmployeeDAO;
import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.vo.Employee;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("employeeManagementService")
@SuppressWarnings({"rawtypes", "unchecked"})
public class EmployeeManagementServiceImpl implements EmployeeManagementService {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Override
	public List<Employee> getAll() {
		return getAll(true);
	}

	@Override
	public List<Employee> getAll(boolean onlyEmployees) {
		try {
			return onlyEmployees ? employeeDAO.getSession().createCriteria(Employee.class).createAlias("role", "empRole").add(Restrictions.eq("empRole.name", "ROLE_EMPLOYEE")).list() : employeeDAO.getAll();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Employee getById(int id) {
		try {
			return employeeDAO.getById(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void save(Employee employee) {
		employeeDAO.save(employee);
	}

	@Override
	public boolean deleteById(int id) {
		try {
			employeeDAO.deleteById(id);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Employee employee) {
		try {
			employeeDAO.delete(employee);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Employee employee) {
		try {
			employeeDAO.update(employee);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Employee getByUserName(String name) {
		try {
			List list = employeeDAO.getSession().createCriteria(Employee.class).add(Restrictions.eq("username", name)).list();
			return list.size() > 0 ? (Employee) list.get(0) : null;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean mergeChanges(Employee employee) {
		try {
			employeeDAO.merge(employee);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Employee> getAllMembers() {
		try {
			return employeeDAO.getSession().createCriteria(Employee.class).createAlias("role", "empRole").add(Restrictions.eq("empRole.name", "ROLE_MEMBER")).list();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
