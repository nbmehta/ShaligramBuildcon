/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.ProjectType;

public interface ProjectTypeDAO extends GenericDAO<ProjectType, Integer> {
	public Session getSession();
}
