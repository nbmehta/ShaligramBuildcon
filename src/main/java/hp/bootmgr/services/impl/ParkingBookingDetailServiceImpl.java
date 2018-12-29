package hp.bootmgr.services.impl;

import hp.bootmgr.dao.ParkingBookingDetailDAO;
import hp.bootmgr.services.ParkingBookingDetailService;
import hp.bootmgr.vo.ParkingBookingDetail;
import hp.bootmgr.vo.ProjectExtraParking;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("parkingBookingDetailService")
@Transactional
@SuppressWarnings("unchecked")

public class ParkingBookingDetailServiceImpl implements ParkingBookingDetailService{
	
	@Autowired
	private ParkingBookingDetailDAO parkingBookingDetailDAO;
	
	@Override
	public List<ParkingBookingDetail> getAll() {
		List<ParkingBookingDetail> ret = null;
		try {
			ret = parkingBookingDetailDAO.getAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public ParkingBookingDetail getById(Integer key) {
		try {
			return parkingBookingDetailDAO.getById(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			parkingBookingDetailDAO.deleteById(key);
			return true;
		
		} catch (Exception ex) {
			
			return false;
		}
	}

	@Override
	public boolean delete(ParkingBookingDetail object) {
		try {
			parkingBookingDetailDAO.delete(object);
			return true;
		
		} catch (Exception ex) {
			
			return false;
		}
	}

	@Override
	public boolean update(ParkingBookingDetail object) {
		try {
			parkingBookingDetailDAO.update(object);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean save(ParkingBookingDetail pb) {
		try {
			parkingBookingDetailDAO.save(pb);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<ProjectExtraParking> getextraParkingForProject(Integer id) {
		List<ProjectExtraParking> ret = null;
		try {
			ret = parkingBookingDetailDAO.getSession().createCriteria(ProjectExtraParking.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("project.id", id)).list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ret;
	
	}
}
