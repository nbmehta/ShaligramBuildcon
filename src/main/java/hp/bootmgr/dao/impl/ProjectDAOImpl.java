package hp.bootmgr.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hp.bootmgr.dao.ProjectDAO;
import hp.bootmgr.vo.Project;

@Repository
@SuppressWarnings("unchecked")
public class ProjectDAOImpl implements ProjectDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Project> getAll() {
		//return sessionFactory.getCurrentSession().createCriteria(Project.class).list();
		// TODO: Disable lazy loading for jsp pages
		return sessionFactory.getCurrentSession().createCriteria(Project.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Override
	public Project getById(Integer key) {
		
		return (Project) sessionFactory.getCurrentSession().get(Project.class, key);
	}

	@Override
	public void deleteById(Integer key) {
	
		sessionFactory.getCurrentSession().delete(getById(key));
	}

	@Override
	public void delete(Project object) {
		sessionFactory.getCurrentSession().delete(object);
		
	}

	@Override
	public void update(Project object) {
	
		sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void save(Project object) {

		sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
