/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services;

import hp.bootmgr.vo.Employee;

import java.util.List;

public interface EmployeeManagementService {
	public List<Employee> getAll(boolean alsoAdmins);
	public List<Employee> getAll();
	public List<Employee> getAllMembers();
	public Employee getById(int id);
	public void save(Employee employee);
	public boolean deleteById(int id);
	public boolean delete(Employee employee);
	public boolean update(Employee employee);
	public Employee getByUserName(String name);
	public boolean mergeChanges(Employee employee);
}
