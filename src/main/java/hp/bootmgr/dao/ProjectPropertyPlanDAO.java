package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.ProjectPropertyPlan;

public interface ProjectPropertyPlanDAO extends  GenericDAO<ProjectPropertyPlan, Integer> {
	public Session getSession();
}
