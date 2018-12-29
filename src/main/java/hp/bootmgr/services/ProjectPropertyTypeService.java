/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services;

import hp.bootmgr.vo.ProjectPropertyType;

import java.util.List;

public interface ProjectPropertyTypeService {
	public List<ProjectPropertyType> getAll();
	public ProjectPropertyType getById(int id);
	public boolean delete(int id);
	public boolean update(ProjectPropertyType projectPropertyType);
	public boolean add(ProjectPropertyType projectPropertyType);
	public boolean deleteById(int id);
}


