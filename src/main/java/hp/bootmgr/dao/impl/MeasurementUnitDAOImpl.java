package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.MeasurementUnitDAO;
import hp.bootmgr.vo.MeasurementUnit;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MeasurementUnitDAOImpl implements MeasurementUnitDAO {
	@Autowired
	private SessionFactory sessionFactory;
	

	@SuppressWarnings("unchecked")
	@Override
	public List<MeasurementUnit> getAll() {
		
		return sessionFactory.getCurrentSession().createCriteria(MeasurementUnit.class).list();
	}

	@Override
	public MeasurementUnit getById(Integer id) {
		
		return (MeasurementUnit) sessionFactory.getCurrentSession().get(MeasurementUnit.class, id);
	}

	@Override
	public void deleteById(Integer key) {
		
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(MeasurementUnit object) {
		
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public void update(MeasurementUnit measurementUnit) {
		
		sessionFactory.getCurrentSession().update(measurementUnit);
	}

	@Override
	public void save(MeasurementUnit m) {
		
		sessionFactory.getCurrentSession().save(m);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}



}
