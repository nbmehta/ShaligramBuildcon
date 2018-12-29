/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services;

import hp.bootmgr.vo.ProjectExtraParking;

import java.util.List;

public interface ProjectExtraParkingService {
	public List<ProjectExtraParking> getAll();
	public ProjectExtraParking getById(Integer key);
	public boolean deleteById(Integer key);
	public boolean delete(ProjectExtraParking projectExtraParking);
	public boolean update(ProjectExtraParking projectExtraParking);
	public boolean save(ProjectExtraParking p);


}
