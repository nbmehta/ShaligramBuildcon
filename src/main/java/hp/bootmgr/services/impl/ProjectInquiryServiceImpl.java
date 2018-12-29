package hp.bootmgr.services.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hp.bootmgr.dao.ProjectInquiryDAO;
import hp.bootmgr.services.ProjectInquiryService;
import hp.bootmgr.vo.ProjectInquiry;

@Transactional
@Service("projectInquiryService")
public class ProjectInquiryServiceImpl implements ProjectInquiryService{
	
	@Autowired
	private ProjectInquiryDAO projectInquiryDAO;
	
	@Override
	public List<ProjectInquiry> getAll() {
		List<ProjectInquiry> ret = null;
		try {
			ret = projectInquiryDAO.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public ProjectInquiry getById(Integer key) {
		try {
			return projectInquiryDAO.getById(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			projectInquiryDAO.deleteById(key);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean delete(ProjectInquiry object) {
		try {
			projectInquiryDAO.delete(object);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean update(ProjectInquiry projectInquiry) {
		try {
			projectInquiryDAO.update(projectInquiry);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean save(ProjectInquiry p) {
		try {
			projectInquiryDAO.save(p);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public int getInquiryCount(int projectId) {
		try {
			return projectInquiryDAO.getSession().createCriteria(ProjectInquiry.class)
					.createAlias("project", "p")
					.add(Restrictions.eq("p.id", projectId))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list().size();
		} catch(Exception ex) {
			return 0;
		}
	}

}
