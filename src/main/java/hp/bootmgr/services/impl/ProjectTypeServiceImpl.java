/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services.impl;

import hp.bootmgr.dao.ProjectTypeDAO;
import hp.bootmgr.services.ProjectTypeService;
import hp.bootmgr.vo.ProjectType;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Win
 *
 */
@Transactional
@Service("projectTypeService")
public class ProjectTypeServiceImpl implements ProjectTypeService {

	@Autowired
	private ProjectTypeDAO projectTypeDAO;

	@Override
	public List<ProjectType> getAll() {
		try {
			return projectTypeDAO.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ProjectType getById(int id) {
		try {
			return projectTypeDAO.getById(id);
		} catch (Exception ex) {
			return null;
		}

	}

	@Override
	public boolean update(ProjectType projectType) {
		try {
			projectTypeDAO.update(projectType);
			return true;

		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean add(ProjectType projectType) {
		try {
			projectTypeDAO.save(projectType);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean deleteById(int id) {
		try {
			projectTypeDAO.deleteById(id);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(ProjectType projectType) {
		try {
			projectTypeDAO.delete(projectType);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean isAlreadyExist(String name) {
		try {
		return	projectTypeDAO.getSession().createCriteria(ProjectType.class).add(Restrictions.eq("name", name)).list().size() > 0;
				
		} catch (Exception ex) {
			return false;
		}
	}

}
