/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.Employee;

public interface EmployeeDAO extends GenericDAO<Employee, Integer>{
	public Session getSession();
	public void merge(Employee employee);
}
