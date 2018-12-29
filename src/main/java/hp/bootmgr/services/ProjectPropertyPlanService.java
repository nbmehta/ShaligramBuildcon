package hp.bootmgr.services;

import hp.bootmgr.vo.ProjectPropertyPlan;

import java.util.List;

public interface ProjectPropertyPlanService {
	public List<ProjectPropertyPlan> getAll();
	public ProjectPropertyPlan getById(Integer key);
	public boolean deleteById(Integer key);
	public boolean delete(ProjectPropertyPlan projectPropertyPlan);
	public boolean update(ProjectPropertyPlan projectPropertyPlan);
	public boolean save(ProjectPropertyPlan p);
	public List<ProjectPropertyPlan> getPlansForProject(int projectID);
	public List<ProjectPropertyPlan> getPlansForPropertyType(int propertyTypeID);
	public List<ProjectPropertyPlan> getPlansForBlock(int blockID);
}
