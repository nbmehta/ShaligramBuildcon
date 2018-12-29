/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.PaymentPlan;

public interface PaymentDAO extends GenericDAO<PaymentPlan, Integer> {
	public Session getSession();
}
