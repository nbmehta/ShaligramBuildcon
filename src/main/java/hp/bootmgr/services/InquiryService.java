/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.services;

import hp.bootmgr.vo.Inquiry;

import java.util.List;

public interface InquiryService {
	public List<Inquiry> getAll();
	public Inquiry getById(Integer id);
	public boolean deleteById(Integer key);
	public boolean delete(Inquiry object);
	public boolean update(Inquiry inquiry);
	public boolean save(Inquiry m);
	public List<Inquiry> getFilteredInquiries(int offset, int count, String searchPattern);
	public String getInquiriesPerMonth();
}
