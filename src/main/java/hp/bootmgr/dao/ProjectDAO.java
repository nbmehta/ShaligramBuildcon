package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.Project;

public interface ProjectDAO extends GenericDAO<Project, Integer>{
	public Session getSession();
}
