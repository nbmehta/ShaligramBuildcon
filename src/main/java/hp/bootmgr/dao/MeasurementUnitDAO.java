package hp.bootmgr.dao;



import org.hibernate.Session;

import hp.bootmgr.vo.MeasurementUnit;

public interface MeasurementUnitDAO extends GenericDAO<MeasurementUnit, Integer>{
	public Session getSession();
}
