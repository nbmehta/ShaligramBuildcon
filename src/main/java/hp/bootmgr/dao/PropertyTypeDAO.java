/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao;


import org.hibernate.Session;

import hp.bootmgr.vo.PropertyType;

public interface PropertyTypeDAO extends GenericDAO<PropertyType, Integer> {
	public Session getSession();
}
