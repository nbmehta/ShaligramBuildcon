package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.ParkingBookingDetail;

public interface ParkingBookingDetailDAO extends GenericDAO<ParkingBookingDetail, Integer>{
	public Session getSession();
}
