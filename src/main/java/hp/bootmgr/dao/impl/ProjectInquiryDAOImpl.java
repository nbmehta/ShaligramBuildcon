package hp.bootmgr.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hp.bootmgr.dao.ProjectInquiryDAO;
import hp.bootmgr.vo.ProjectInquiry;

@Repository
@SuppressWarnings("unchecked")
public class ProjectInquiryDAOImpl implements ProjectInquiryDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<ProjectInquiry> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(ProjectInquiry.class).list();
	}

	@Override
	public ProjectInquiry getById(Integer key) {
		return (ProjectInquiry) sessionFactory.getCurrentSession().get(ProjectInquiry.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));
		
	}

	@Override
	public void delete(ProjectInquiry object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public void update(ProjectInquiry object) {
		sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void save(ProjectInquiry p) {
		sessionFactory.getCurrentSession().save(p);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
