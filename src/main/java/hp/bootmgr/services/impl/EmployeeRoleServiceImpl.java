package hp.bootmgr.services.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hp.bootmgr.dao.EmployeeRoleDAO;
import hp.bootmgr.services.EmployeeRoleService;
import hp.bootmgr.vo.EmployeeRole;

@Transactional
@Service("employeeRoleService")
@SuppressWarnings("unchecked")
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

	@Autowired
	private EmployeeRoleDAO employeeRoleDAO;

	@Override
	public List<EmployeeRole> getAll() {

		List<EmployeeRole> ret = null;
		try {
			ret = employeeRoleDAO.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public EmployeeRole getById(Integer key) {
		try {
			return employeeRoleDAO.getById(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			employeeRoleDAO.deleteById(key);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public boolean delete(EmployeeRole object) {
		try {
			employeeRoleDAO.delete(object);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean update(EmployeeRole employeeRole) {
		try {
			employeeRoleDAO.update(employeeRole);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean save(EmployeeRole e) {
		try {
			employeeRoleDAO.save(e);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean isAlreadyExist(String name) {
		try {
			return employeeRoleDAO.getSession().createCriteria(EmployeeRole.class).add(Restrictions.eq("name", name)).list().size() > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public EmployeeRole getRoleByName(String name) {
		try {
			List<EmployeeRole> roles = employeeRoleDAO.getSession().createCriteria(EmployeeRole.class).add(Restrictions.eq("name", name)).list();
			return roles.size() > 0 ? roles.get(0) : null;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
