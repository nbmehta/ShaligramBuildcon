package hp.bootmgr.services.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hp.bootmgr.dao.MeasurementUnitDAO;
import hp.bootmgr.services.MeasurementUnitService;
import hp.bootmgr.vo.MeasurementUnit;
@Transactional
@Service("measurementUnitService")
public class MeasurementUnitServiceImpl implements MeasurementUnitService {
	
	@Autowired
	private MeasurementUnitDAO measurementUnitDAO;
	
	@Override
	public List<MeasurementUnit> getAll() {
		try {
			return measurementUnitDAO.getAll();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public MeasurementUnit getById(Integer id) {
		try {
			return measurementUnitDAO.getById(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			measurementUnitDAO.deleteById(key);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(MeasurementUnit object) {
		try {
			measurementUnitDAO.delete(object);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(MeasurementUnit measurementUnit) {
		try {
			measurementUnitDAO.update(measurementUnit);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean save(MeasurementUnit m) {
		try {
			measurementUnitDAO.save(m);
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isAlreadyAvailable(String name) {
		try{
			return measurementUnitDAO.getSession().createCriteria(MeasurementUnit.class).add(Restrictions.eq("name", name)).list().size() > 0;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
			
		}
	}

}
