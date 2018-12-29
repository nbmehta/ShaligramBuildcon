package hp.bootmgr.services;

import hp.bootmgr.vo.EmployeeRole;

import java.util.List;

public interface EmployeeRoleService {
	public List<EmployeeRole> getAll();
	public EmployeeRole getById(Integer key);
	public boolean deleteById(Integer key);
	public boolean delete(EmployeeRole object);
	public boolean update(EmployeeRole employeeRole);
	public boolean save(EmployeeRole e);
	public boolean isAlreadyExist(String name);
	public EmployeeRole getRoleByName(String name);
}