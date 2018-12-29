package hp.bootmgr.services;

import hp.bootmgr.vo.MeasurementUnit;

import java.util.List;

public interface MeasurementUnitService {
	public List<MeasurementUnit> getAll();
	public MeasurementUnit getById(Integer id);
	public boolean deleteById(Integer key);
	public boolean delete(MeasurementUnit object);
	public boolean update(MeasurementUnit measurementUnit);
	public boolean save(MeasurementUnit m);
	public boolean isAlreadyAvailable(String name);
}
