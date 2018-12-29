package hp.bootmgr.dao;

import org.hibernate.Session;

import hp.bootmgr.vo.ProjectInquiry;

public interface ProjectInquiryDAO extends GenericDAO<ProjectInquiry, Integer>{
	public Session getSession();
}
