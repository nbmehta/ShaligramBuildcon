 package hp.bootmgr.web.services;

 import hp.bootmgr.services.ProjectTypeService;
 import hp.bootmgr.vo.ProjectType;
 import hp.bootmgr.web.services.responses.Result;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.RestController;

 import java.util.List;

@RestController
public class GetProjectTypeApi {
	
	@Autowired
	private ProjectTypeService projectTypeService;
	
	@RequestMapping(value = "/api/admin/getPojectType", method = RequestMethod.POST)
	public ResponseEntity<List<ProjectType>> listAllProjectType() {
		List<ProjectType> projectTypes = projectTypeService.getAll();
		if(projectTypes.isEmpty()){
			return new ResponseEntity<List<ProjectType>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ProjectType>>(projectTypes, HttpStatus.OK);
		
	}
	@RequestMapping(value="/api/admin/getPojectType/{id}",method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		ProjectType ret =projectTypeService.getById(idi);
		return ret == null ? new Result("Project Type  not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}
	
	@RequestMapping(value = "/api/admin/deletePojectType/{id}", method = RequestMethod.DELETE)
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
			ret = projectTypeService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation", ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("State not found", ResultCodes.STATE_NOT_FOUND);
		}
	}
