package hp.bootmgr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hp.bootmgr.dao.ProjectExtraParkingDAO;
import hp.bootmgr.services.ProjectExtraParkingService;
import hp.bootmgr.vo.ProjectExtraParking;

@Transactional
@Service("projectExtraParkingService")
public class ProjectExtraParkingServiceImpl implements ProjectExtraParkingService{
	
	@Autowired
	private ProjectExtraParkingDAO projectExtraParkingDAO;
	
	@Override
	public List<ProjectExtraParking> getAll() {
	
		List<ProjectExtraParking> ret = null;
		try {
			ret = projectExtraParkingDAO.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public ProjectExtraParking getById(Integer key) {
	
		try {
			return projectExtraParkingDAO.getById(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		
		try {
			projectExtraParkingDAO.deleteById(key);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean delete(ProjectExtraParking projectExtraParking) {

		try {
			projectExtraParkingDAO.delete(projectExtraParking);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean update(ProjectExtraParking projectExtraParking) {
	
		try {
			projectExtraParkingDAO.update(projectExtraParking);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean save(ProjectExtraParking p) {

		try {
			projectExtraParkingDAO.save(p);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
