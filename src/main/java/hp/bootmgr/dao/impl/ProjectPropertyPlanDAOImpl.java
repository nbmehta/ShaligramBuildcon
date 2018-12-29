package hp.bootmgr.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hp.bootmgr.dao.ProjectPropertyPlanDAO;
import hp.bootmgr.vo.ProjectPropertyPlan;

@Repository
@SuppressWarnings("unchecked")
public class ProjectPropertyPlanDAOImpl implements ProjectPropertyPlanDAO {
	@Autowired
	private SessionFactory sessionFactory;
		
	@Override
	public List<ProjectPropertyPlan> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(ProjectPropertyPlan.class).list();
	}

	@Override
	public ProjectPropertyPlan getById(Integer key) {
		return (ProjectPropertyPlan) sessionFactory.getCurrentSession().get(ProjectPropertyPlan.class, key) ;
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(key);
	}

	@Override
	public void delete(ProjectPropertyPlan object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public void update(ProjectPropertyPlan object) {
		sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void save(ProjectPropertyPlan object) {
		sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
