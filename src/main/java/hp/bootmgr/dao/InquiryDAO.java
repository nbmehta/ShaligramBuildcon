/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.Inquiry;

public interface InquiryDAO extends GenericDAO<Inquiry, Integer> {
	public Session getSession();
}
