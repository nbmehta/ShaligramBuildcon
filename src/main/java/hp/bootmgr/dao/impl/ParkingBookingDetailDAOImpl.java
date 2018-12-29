package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.ParkingBookingDetailDAO;
import hp.bootmgr.vo.ParkingBookingDetail;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingBookingDetailDAOImpl implements ParkingBookingDetailDAO{
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<ParkingBookingDetail> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(ParkingBookingDetail.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public ParkingBookingDetail getById(Integer key) {
		return (ParkingBookingDetail) sessionFactory.getCurrentSession().get(ParkingBookingDetail.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		
	 sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(ParkingBookingDetail object) {
	sessionFactory.getCurrentSession().delete(object);
		
	}

	@Override
	public void update(ParkingBookingDetail object) {

	sessionFactory.getCurrentSession().update(object);	
	}

	@Override
	public void save(ParkingBookingDetail object) {
	sessionFactory.getCurrentSession().save(object);
	}
	
	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
