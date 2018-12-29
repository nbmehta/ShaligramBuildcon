package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.ProjectService;
import hp.bootmgr.vo.Project;

import hp.bootmgr.web.services.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProjectApi {
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/api/admin/getProject", method = RequestMethod.POST)
	public ResponseEntity<List<Project>> listAllState() {
		List<Project> projects = projectService.getAll();
		if(projects.isEmpty()){
			return new ResponseEntity<List<Project>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
	}
	@RequestMapping(value = "/api/admin/getProject/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		Project ret = projectService.getById(idi);
		return ret == null ? new Result("Project not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}
	@RequestMapping(value = "/api/admin/deleteProject/{id}", method = RequestMethod.DELETE)
	public Object deleteById(@PathVariable("id") String id) {
		Integer idi = null;
		boolean ret = false;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		try {
			ret = projectService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation", ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("Project not found", ResultCodes.STATE_NOT_FOUND);
	}


}
