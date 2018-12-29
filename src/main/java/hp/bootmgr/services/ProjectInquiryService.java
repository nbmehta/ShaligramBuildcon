package hp.bootmgr.services;

import hp.bootmgr.vo.ProjectInquiry;

import java.util.List;

public interface ProjectInquiryService {
	public List<ProjectInquiry> getAll();
	public ProjectInquiry getById(Integer key);
	public boolean deleteById(Integer key);
	public boolean delete(ProjectInquiry object);
	public boolean update(ProjectInquiry projectInquiry);
	public boolean save(ProjectInquiry p);
	public int getInquiryCount(int projectId);
}
