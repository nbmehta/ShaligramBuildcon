package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.ProjectPropertyPlanService;
import hp.bootmgr.vo.ProjectPropertyPlan;

import hp.bootmgr.web.services.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetPropertyPlanApi {
	
	@Autowired
	private ProjectPropertyPlanService projectPropertyPlanService;
	
	@RequestMapping(value = "/api/admin/getPropertyPlan", method = RequestMethod.POST)
	public ResponseEntity<List<ProjectPropertyPlan>> listAllState() {
		List<ProjectPropertyPlan> projectPropertyPlans = projectPropertyPlanService.getAll();
		if(projectPropertyPlans.isEmpty()){
			return new ResponseEntity<List<ProjectPropertyPlan>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ProjectPropertyPlan>>(projectPropertyPlans, HttpStatus.OK);
	}
	@RequestMapping(value = "/api/admin/getPropertyPlan/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		ProjectPropertyPlan ret = projectPropertyPlanService.getById(idi);
		return ret == null ? new Result("Property Plan not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}
	@RequestMapping(value = "/api/admin/deletePropertyPlan/{id}", method = RequestMethod.DELETE)
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
			ret = projectPropertyPlanService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation", ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("Property Plan not found", ResultCodes.STATE_NOT_FOUND);
	}

}
