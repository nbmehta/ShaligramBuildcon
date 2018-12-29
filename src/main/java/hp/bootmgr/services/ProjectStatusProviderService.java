/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services;

import hp.bootmgr.vo.ProjectStatus;

import java.util.List;

public interface ProjectStatusProviderService {
	public ProjectStatus getProjectStatusById(int id);
	public List<ProjectStatus> getProjectStatus();
	public boolean addProjectStatus(ProjectStatus projectStatus);
	public boolean updateProjectStatus(ProjectStatus projectStatus);
	public boolean deleteProjectStatus(int id);
	public boolean isAlreadyExist(String name);

}
