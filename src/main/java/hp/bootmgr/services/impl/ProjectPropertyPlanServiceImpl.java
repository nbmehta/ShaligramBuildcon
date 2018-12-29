package hp.bootmgr.services.impl;

import hp.bootmgr.dao.ProjectPropertyPlanDAO;
import hp.bootmgr.services.ProjectPropertyPlanService;
import hp.bootmgr.vo.ProjectPropertyPlan;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@SuppressWarnings("unchecked")
@Service("projectPropertyPlanService")
public class ProjectPropertyPlanServiceImpl implements ProjectPropertyPlanService {
	
	@Autowired
	private ProjectPropertyPlanDAO projectPropertyPlanDAO;

	@Override
	public List<ProjectPropertyPlan> getAll() {
		List<ProjectPropertyPlan> ret = null;
		try {
			ret = projectPropertyPlanDAO.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public ProjectPropertyPlan getById(Integer key) {
		try {
			return projectPropertyPlanDAO.getById(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			projectPropertyPlanDAO.delete(getById(key));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(ProjectPropertyPlan projectPropertyPlan) {
		try {
			projectPropertyPlanDAO.delete(projectPropertyPlan);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(ProjectPropertyPlan projectPropertyPlan) {
		try {
			projectPropertyPlanDAO.update(projectPropertyPlan);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean save(ProjectPropertyPlan p) {
		try {
			projectPropertyPlanDAO.save(p);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<ProjectPropertyPlan> getPlansForProject(int projectID) {
		return projectPropertyPlanDAO.getSession().createCriteria(ProjectPropertyPlan.class)
				.add(Restrictions.eq("project.id", projectID)).list();
	}

	@Override
	public List<ProjectPropertyPlan> getPlansForPropertyType(int propertyTypeID) {
		return projectPropertyPlanDAO.getSession().createCriteria(ProjectPropertyPlan.class)
				.add(Restrictions.eq("propertyType.propertyTypeID", propertyTypeID)).list();
	}

	@Override
	public List<ProjectPropertyPlan> getPlansForBlock(int blockID) {
		return projectPropertyPlanDAO.getSession().createCriteria(ProjectPropertyPlan.class)
				.add(Restrictions.eq("block.blockId", blockID)).list();
	}
}
