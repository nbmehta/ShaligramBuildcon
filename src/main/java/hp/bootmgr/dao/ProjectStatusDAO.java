/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.ProjectStatus;

public interface ProjectStatusDAO extends GenericDAO<ProjectStatus, Integer> {
	public Session getSession();
}
