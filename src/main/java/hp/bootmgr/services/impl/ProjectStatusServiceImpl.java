/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hp.bootmgr.dao.ProjectStatusDAO;
import hp.bootmgr.services.ProjectStatusProviderService;
import hp.bootmgr.vo.ProjectStatus;

/**
 * @author Win
 *
 */
@Transactional
@Service("projectStatusService")
public class ProjectStatusServiceImpl implements ProjectStatusProviderService {
	@Autowired
	private ProjectStatusDAO projectStatusDAO;
	@Override
	public ProjectStatus getProjectStatusById(int id) {
		try{
			return projectStatusDAO.getById(id);
		}catch(Exception ex){
		return null;
		}
	}

	@Override
	public List<ProjectStatus> getProjectStatus() {
		try{
			return projectStatusDAO.getAll();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean addProjectStatus(ProjectStatus projectStatus) {
		try{
			projectStatusDAO.save(projectStatus);
			return true;
			
		}catch(Exception ex){
		ex.printStackTrace();
		return false;
		}
	}

	@Override
	public boolean updateProjectStatus(ProjectStatus projectStatus) {
		try{
			projectStatusDAO.update(projectStatus);
			return true;
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean deleteProjectStatus(int id) {
		try{
			projectStatusDAO.deleteById(id);
			return true;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		return false;
		}
	}

	@Override
	public boolean isAlreadyExist(String name) {
		try{
			return projectStatusDAO.getSession().createCriteria(ProjectStatus.class).add(Restrictions.eq("name", name)).list().size() > 0;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		return false;
		}
	}

}
