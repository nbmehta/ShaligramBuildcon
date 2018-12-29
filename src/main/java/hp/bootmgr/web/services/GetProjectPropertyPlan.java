package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.ProjectPropertyPlanService;
import hp.bootmgr.vo.ProjectPropertyPlan;
import hp.bootmgr.vo.State;
import hp.bootmgr.web.services.responses.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProjectPropertyPlan {

	@Autowired
	private ProjectPropertyPlanService projectPropertyPlanService;
	@RequestMapping(value = "/api/admin/projectPropertyplan", method = RequestMethod.POST)
	public ResponseEntity<List<ProjectPropertyPlan>> projectpropetyPlanlist() {
		List<ProjectPropertyPlan> projectPropertyPlans=projectPropertyPlanService.getAll();
		if (projectPropertyPlans.isEmpty()) {
			return new ResponseEntity<List<ProjectPropertyPlan>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectPropertyPlan>>(projectPropertyPlans, HttpStatus.OK);
	}
		
	@RequestMapping(value="/api/admin/projectpropertyplan/{id}",method=RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect",ResultCodes.INVALID_REQUEST);
		}
		ProjectPropertyPlan ret = projectPropertyPlanService.getById(idi);
		return ret == null ? new Result("project propety plan not found",ResultCodes.STATE_NOT_FOUND) : ret;
	}
	
	@RequestMapping(value="/api/admin/deleteprojectpropertyplan/{id}",method=RequestMethod.POST)
	public Object deleteById(@PathVariable("id") String id) {
		Integer idi = null;
		boolean ret = false;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect",	ResultCodes.INVALID_REQUEST);
		}
		try {
			ret = projectPropertyPlanService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation",	ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("Project property plan  not found",	ResultCodes.STATE_NOT_FOUND);
	}
	@RequestMapping(value="/api/admin/getPlansForProject/{projectID}",method=RequestMethod.POST)
	public ResponseEntity<List<ProjectPropertyPlan>> planForProject(@PathVariable("projectID") Integer projectID) {
		List<ProjectPropertyPlan> projectPropertyPlans=projectPropertyPlanService.getPlansForProject(projectID);
		if (projectPropertyPlans.isEmpty()) {
			return new ResponseEntity<List<ProjectPropertyPlan>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectPropertyPlan>>(projectPropertyPlans, HttpStatus.OK);
	}
	@RequestMapping(value="/api/admin/getplansforpropertyType/{propertyTypeID}",method=RequestMethod.POST)
	public ResponseEntity<List<ProjectPropertyPlan>> planForPropertyPlan(@RequestParam("propertyTypeID")int id){
		List<ProjectPropertyPlan> projectPropertyPlans=projectPropertyPlanService.getPlansForPropertyType(id);
		if(projectPropertyPlans.isEmpty())
		{
			return new ResponseEntity<List<ProjectPropertyPlan>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectPropertyPlan>>(projectPropertyPlans,HttpStatus.OK);
		
	}
	@RequestMapping(value="/api/admin/getplansforpropertyType/{blockID}",method=RequestMethod.POST)
	public ResponseEntity<List<ProjectPropertyPlan>> getPlansForBlock(@RequestParam("blockID") int id)
	{
		List<ProjectPropertyPlan> projectPropertyPlans=projectPropertyPlanService.getPlansForBlock(id);
		if(projectPropertyPlans.isEmpty()){
			return new ResponseEntity<List<ProjectPropertyPlan>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectPropertyPlan>>(projectPropertyPlans,HttpStatus.OK);
	}
	
}
