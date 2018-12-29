package hp.bootmgr.services.impl;

import hp.bootmgr.dao.ProjectDAO;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.vo.BookingDetail;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectFile;
import hp.bootmgr.vo.PropertyDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Override
	public List<Project> getAll() {
		try {
			return projectDAO.getAll();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Project getById(Integer key) {
		try {
			return projectDAO.getById(key);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			projectDAO.deleteById(key);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Project object) {
		try {
			projectDAO.delete(object);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Project project) {
		try {
			projectDAO.update(project);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean save(Project p) {
		try {
			projectDAO.save(p);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public int getPropertyCountForProject(int projectId) {
		try {
			return projectDAO.getSession().createCriteria(PropertyDetail.class)
					.createAlias("project", "p")
					.add(Restrictions.eq("p.id", projectId))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list().size();
		}
		catch(Exception ex) {
			return 0;
		}
	}

	@Override
	public int getBookedPropertyCountForProject(int projectId) {
		try {
			return projectDAO.getSession().createCriteria(BookingDetail.class)
					.createAlias("project", "p")
					.add(Restrictions.eq("p.id", projectId))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list().size();
		}
		catch(Exception ex) {
			return 0;
		}
	}

	@Override
	public Project getProjectByName(String name) {
		try {
			List projects = projectDAO.getSession().createCriteria(Project.class)
					.add(Restrictions.like("name", name))
					.list();
			return projects.size() > 0 ? (Project) projects.get(0) : null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ProjectFile getProjectFile(Integer id) {
		try {
            return  projectDAO.getSession().get(ProjectFile.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
	}
}
