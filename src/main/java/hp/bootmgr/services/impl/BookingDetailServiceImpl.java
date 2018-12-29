package hp.bootmgr.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hp.bootmgr.dao.BookingDetailDAO;
import hp.bootmgr.services.BookingDetailService;
import hp.bootmgr.vo.BookingDetail;

@Transactional
@Service("bookingDetailService")
@SuppressWarnings("unchecked")
public class BookingDetailServiceImpl implements BookingDetailService {
	@Autowired
	private BookingDetailDAO bookingDetailDAO;

	@Override
	public List<BookingDetail> getAll() {
		List<BookingDetail> ret = null;
		try {
			ret = bookingDetailDAO.getAll();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	public BookingDetail getById(Integer key) {
		try {
			return bookingDetailDAO.getById(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(Integer key) {
		try {
			bookingDetailDAO.deleteById(key);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(BookingDetail object) {
		try {
			bookingDetailDAO.delete(object);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(BookingDetail bookingDetail) {
		try {
			bookingDetailDAO.update(bookingDetail);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean save(BookingDetail e) {
		try {
			bookingDetailDAO.save(e);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public int getBookingCount(int projectId) {
		try {
			return bookingDetailDAO.getSession().createCriteria(BookingDetail.class)
					.createAlias("project", "p")
					.add(Restrictions.eq("p.id", projectId))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list().size();
		} catch(Exception ex) {
			return 0;
		}
	}

	@Override
	public String getBookingPerMonth() {
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		StringBuilder bookingBuilder = new StringBuilder("[");
		try{
			for(int i=1; i < 13; i++){
			    cal.set(Calendar.DATE, 1);
			    cal.set(Calendar.MONTH, (i - 1));
			    
			    Date startDate=cal.getTime();
			    int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			    
			    cal.set(Calendar.DATE, maxDay);
			    cal.set(Calendar.HOUR_OF_DAY, 23);
			    cal.set(Calendar.MINUTE, 59);
			    cal.set(Calendar.SECOND, 59);
			    cal.set(Calendar.MILLISECOND, 999);
			    
			    Date endDate = cal.getTime();
			    int n= bookingDetailDAO.getSession().createCriteria(BookingDetail.class)
						.add(Restrictions.between("bookingDate",startDate,endDate))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.list().size();
			    
			    bookingBuilder.append(n);
				bookingBuilder.append(",");
		    }
			if(bookingBuilder.length() > 0)
				bookingBuilder.deleteCharAt(bookingBuilder.length() - 1);
			bookingBuilder.append("]");
		}
		catch(Exception e){
			e.printStackTrace();
			return "[]";
		}
		return bookingBuilder.toString();
	}

	@Override
	public List<BookingDetail> getPropertiesBookedByMember(int employeeId) {
		try {
			return bookingDetailDAO.getSession().createCriteria(BookingDetail.class)
				.createAlias("memberDetail", "owner")
				.add(Restrictions.eq("owner.id", employeeId))
				.list();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
