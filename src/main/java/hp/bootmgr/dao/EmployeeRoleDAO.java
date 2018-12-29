package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.EmployeeRole;

public interface EmployeeRoleDAO extends GenericDAO<EmployeeRole, Integer>{
	public Session getSession();
}
