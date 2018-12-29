package hp.bootmgr.services;

import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectFile;

import java.util.List;

public interface ProjectService {
	List<Project> getAll();
	Project getById(Integer key);
	boolean deleteById(Integer key);
	boolean delete(Project object);
	boolean update(Project project);
	boolean save(Project p);
	int getPropertyCountForProject(int projectId);
	int getBookedPropertyCountForProject(int projectId);
	Project getProjectByName(String name);
	ProjectFile getProjectFile(Integer id);
}
