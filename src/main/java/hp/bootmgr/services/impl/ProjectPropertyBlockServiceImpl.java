package hp.bootmgr.services.impl;

import hp.bootmgr.dao.ProjectPropertyBlockDAO;
import hp.bootmgr.services.ProjectPropertyBlockService;
import hp.bootmgr.vo.ProjectPropertyBlock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("projectPropertyBlockService")
public class ProjectPropertyBlockServiceImpl implements ProjectPropertyBlockService{
	
	@Autowired
	private ProjectPropertyBlockDAO projectPropertyBlockDAO;
	
	@Override
	public List<ProjectPropertyBlock> getAll() {
		List<ProjectPropertyBlock> ret = null;
		try {
			ret = projectPropertyBlockDAO.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public ProjectPropertyBlock getById(Integer key) {
		try {
			return projectPropertyBlockDAO.getById(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			projectPropertyBlockDAO.deleteById(key);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean delete(ProjectPropertyBlock object) {
		try {
			projectPropertyBlockDAO.delete(object);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean update(ProjectPropertyBlock object) {
		try {
			projectPropertyBlockDAO.update(object);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean save(ProjectPropertyBlock p) {
		try {
			projectPropertyBlockDAO.save(p);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
}
