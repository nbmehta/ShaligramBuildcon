/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services;

import hp.bootmgr.vo.ProjectType;

import java.util.List;

/**
 * @author Win
 *
 */
public interface ProjectTypeService  {
	public List<ProjectType> getAll();
	public ProjectType getById(int id);
	public boolean deleteById(int id);
	public boolean delete(ProjectType projectType);
	public boolean update(ProjectType projectType);
	public boolean add(ProjectType projectType);
	public boolean isAlreadyExist(String name);
}
