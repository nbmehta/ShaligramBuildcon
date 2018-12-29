package hp.bootmgr.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hp.bootmgr.dao.PaymentDAO;
import hp.bootmgr.vo.PaymentPlan;

@SuppressWarnings("unchecked")
@Repository
public class PaymentDAOImpl implements PaymentDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PaymentPlan> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(PaymentPlan.class).list();
	}

	@Override
	public PaymentPlan getById(Integer key) {
		return (PaymentPlan) sessionFactory.getCurrentSession().get(PaymentPlan.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(PaymentPlan object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public void update(PaymentPlan object) {
		sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void save(PaymentPlan object) {
		sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
