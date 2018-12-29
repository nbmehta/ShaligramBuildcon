package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.BookingDetail;

public interface BookingDetailDAO extends GenericDAO<BookingDetail, Integer> {
	public Session getSession();
}
