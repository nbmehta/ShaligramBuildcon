/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services.impl;

import hp.bootmgr.dao.ProjectPropertyTypeDAO;
import hp.bootmgr.services.ProjectPropertyTypeService;
import hp.bootmgr.vo.ProjectPropertyType;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Win
 *
 */
@Transactional
@Service("projectPropertyType")
public class ProjectPropertyTypeServiceImpl implements ProjectPropertyTypeService {
	@Autowired
	private ProjectPropertyTypeDAO projectPropertyTypeDAO;

	@Override
	public List<ProjectPropertyType> getAll() {
		List<ProjectPropertyType> ret=null;
		try{
			ret=projectPropertyTypeDAO.getAll();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return ret;
	}

	@Override
	public ProjectPropertyType getById(int id) {
		try{
			return projectPropertyTypeDAO.getById(id);
			
		}catch(Exception ex)
		{	ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean delete(int id) {
		try{
			projectPropertyTypeDAO.delete(getById(id));
			return true;
	
		}catch(Exception ex)
		{
			ex.printStackTrace();
		return false;
		}
		
	}

	@Override
	public boolean update(ProjectPropertyType projectPropertyType) {
		try{
		projectPropertyTypeDAO.update(projectPropertyType);	
		return true;
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		return false;
		}
	}

	@Override
	public boolean add(ProjectPropertyType projectPropertyType) {
	try{
		projectPropertyTypeDAO.save(projectPropertyType);
		return true;
		
	}catch(Exception ex)
	{
		ex.printStackTrace();
		return false;
	}
		
	}

	@Override
	public boolean deleteById(int id) {
		try {
			projectPropertyTypeDAO.deleteById(id);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	


}
