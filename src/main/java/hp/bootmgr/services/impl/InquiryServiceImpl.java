/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hp.bootmgr.dao.InquiryDAO;
import hp.bootmgr.services.InquiryService;
import hp.bootmgr.vo.Inquiry;

/**
 * @author Win
 *
 */
@Transactional
@Service("InquiryService")
@SuppressWarnings("unchecked")
public class InquiryServiceImpl implements InquiryService {
	@Autowired
	private InquiryDAO InquiryDAO;

	@Override
	public List<Inquiry> getAll() {
		try{
			return InquiryDAO.getAll();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	
	}

	@Override
	public Inquiry getById(Integer id) {
		try{
			return InquiryDAO.getById(id);
			
		}catch(Exception ex)
		{
		ex.printStackTrace();
		return null;
			
		}
		
	}

	@Override
	public boolean deleteById(Integer key) {
		try{
		InquiryDAO.deleteById(key);
		return true;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		return false;
		}
	}

	@Override
	public boolean delete(Inquiry object) {
		try{
			InquiryDAO.delete(object);
			return true;
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
		
	}

	@Override
	public boolean update(Inquiry inquiry) {
		try{
			InquiryDAO.update(inquiry);
			return true;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean save(Inquiry m) {
		try{
			InquiryDAO.save(m);
			return true;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}

	
	/**
	 * getFilteredInquiries(): Returns filtered records. This function works as a slave for
	 * DataTable.
	 * 
	 * TODO: Add sorting capabilities
	 * TODO: Add Search by Date functionality 
	 */
	@Override
	public List<Inquiry> getFilteredInquiries(int offset, int count, String searchPattern) {
		Criteria criteria = InquiryDAO.getSession().createCriteria(Inquiry.class);
		criteria.setFirstResult(offset);
		criteria.setMaxResults(count);
		if(!searchPattern.trim().equals("")) {
			criteria.createAlias("visitedSite", "siteName");
			criteria.add(Restrictions.or(
				Restrictions.ilike("siteName.name", searchPattern, MatchMode.ANYWHERE),
				Restrictions.ilike("visitorName", searchPattern, MatchMode.ANYWHERE),
//				Restrictions.ilike("visitDate", searchPattern, MatchMode.ANYWHERE),
				Restrictions.ilike("contactNumber", searchPattern, MatchMode.ANYWHERE),
				Restrictions.ilike("remark", searchPattern, MatchMode.ANYWHERE)
			));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public String getInquiriesPerMonth() {
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		
		// set today's Date
		cal.setTime(date);
		
		// start of day
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		StringBuilder inquiryBuilder = new StringBuilder("[");
		try{
			for(int i=1; i < 13; i++) {
				// start of month
			    cal.set(Calendar.DATE, 1);
			    cal.set(Calendar.MONTH, (i - 1));
			    
			    Date startDate = cal.getTime();
			    
			    // get the last date of month
			    int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			    
			    // Last date of month
			    cal.set(Calendar.DATE, maxDay);
			    cal.set(Calendar.HOUR_OF_DAY, 23);
			    cal.set(Calendar.MINUTE, 59);
			    cal.set(Calendar.SECOND, 59);
			    cal.set(Calendar.MILLISECOND, 999);
			    
			    // Last date of month
			    Date endDate = cal.getTime();
			    
			    int n = InquiryDAO.getSession().createCriteria(Inquiry.class)
						.add(Restrictions.between("visitDate",startDate,endDate))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.list().size();
			    inquiryBuilder.append(n);
				inquiryBuilder.append(",");
			}
			if(inquiryBuilder.length() > 0)
				inquiryBuilder.deleteCharAt(inquiryBuilder.length() - 1);
			inquiryBuilder.append("]");
		}
		catch(Exception e){
			e.printStackTrace();
			return "[]";
		}
		return inquiryBuilder.toString();
	}
}
