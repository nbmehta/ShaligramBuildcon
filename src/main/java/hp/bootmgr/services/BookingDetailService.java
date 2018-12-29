package hp.bootmgr.services;

import java.util.List;

import hp.bootmgr.vo.BookingDetail;

public interface BookingDetailService {
	public List<BookingDetail> getAll();
	public BookingDetail getById(Integer key);
	public boolean deleteById(Integer key);
	public boolean delete(BookingDetail object);
	public boolean update(BookingDetail bookingDetail);
	public boolean save(BookingDetail e);
	public int getBookingCount(int projectId);
	public String getBookingPerMonth();
	public List<BookingDetail> getPropertiesBookedByMember(int member_id);
}
