package hp.bootmgr.dao.impl;

import hp.bootmgr.dao.PropertyDetailDAO;
import hp.bootmgr.vo.PropertyDetail;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class PropertyDetailDAOImpl implements PropertyDetailDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<PropertyDetail> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(PropertyDetail.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public PropertyDetail getById(Integer key) {
		return (PropertyDetail) sessionFactory.getCurrentSession().get(PropertyDetail.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(PropertyDetail object) {
		sessionFactory.getCurrentSession().delete(object);
		
	}

	@Override
	public void update(PropertyDetail object) {
		sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void save(PropertyDetail P) {
		sessionFactory.getCurrentSession().save(P);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public StatelessSession getStatelessSession() {
		return sessionFactory.openStatelessSession();
	}
}
