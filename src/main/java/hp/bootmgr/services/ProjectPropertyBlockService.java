package hp.bootmgr.services;

import hp.bootmgr.vo.ProjectPropertyBlock;

import java.util.List;

public interface ProjectPropertyBlockService {
	public List<ProjectPropertyBlock> getAll();
	public ProjectPropertyBlock getById(Integer key);
	public boolean deleteById(Integer key);
	public boolean delete(ProjectPropertyBlock object);
	public boolean update(ProjectPropertyBlock object);
	public boolean save(ProjectPropertyBlock p);
}
