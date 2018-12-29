package hp.bootmgr.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hp.bootmgr.dao.ProjectPropertyBlockDAO;
import hp.bootmgr.vo.ProjectPropertyBlock;

@Repository
@SuppressWarnings("unchecked")
public class ProjectPropertyBlockDAOImpl implements ProjectPropertyBlockDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ProjectPropertyBlock> getAll() {

		return sessionFactory.getCurrentSession()
				.createCriteria(ProjectPropertyBlock.class).list();
	}

	@Override
	public ProjectPropertyBlock getById(Integer key) {
		return (ProjectPropertyBlock) sessionFactory.getCurrentSession().get(
				ProjectPropertyBlock.class, key);
	}

	@Override
	public void deleteById(Integer key) {
		sessionFactory.getCurrentSession().delete(getById(key));

	}

	@Override
	public void delete(ProjectPropertyBlock object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	@Override
	public void update(ProjectPropertyBlock object) {
		sessionFactory.getCurrentSession().update(object);
	}

	@Override
	public void save(ProjectPropertyBlock p) {
		sessionFactory.getCurrentSession().save(p);
	}

}
