package hp.bootmgr.services;

import hp.bootmgr.vo.ParkingBookingDetail;
import hp.bootmgr.vo.ProjectExtraParking;

import java.util.List;

public interface ParkingBookingDetailService {
	public List<ParkingBookingDetail> getAll();
	public ParkingBookingDetail getById(Integer key);
	public boolean deleteById(Integer key);
	public boolean delete(ParkingBookingDetail object);
	public boolean update(ParkingBookingDetail object);
	public boolean save(ParkingBookingDetail pb);
	public List<ProjectExtraParking> getextraParkingForProject(Integer id);
}
