package hp.bootmgr.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hp.bootmgr.dao.BookingDetailDAO;
import hp.bootmgr.vo.BookingDetail;

@Repository
@SuppressWarnings("unchecked")
public class BookingDetailDAOImpl implements BookingDetailDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<BookingDetail> getAll() {
		return	sessionFactory.getCurrentSession().createCriteria(BookingDetail.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		
	}
	
	@Override
	public BookingDetail getById(Integer key) {
		return(BookingDetail)sessionFactory.getCurrentSession().get(BookingDetail.class, key);
		
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
		
	}

	@Override
	public void delete(BookingDetail object) {
	sessionFactory.getCurrentSession().delete(object);
		
	}

	@Override
	public void update(BookingDetail object) {
		sessionFactory.getCurrentSession().update(object);
		
	}

	@Override
	public void save(BookingDetail object) {
		sessionFactory.getCurrentSession().save(object);
		
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	

	

}
