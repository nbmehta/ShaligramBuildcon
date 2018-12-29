package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.ProjectStatusProviderService;
import hp.bootmgr.vo.ProjectStatus;

import hp.bootmgr.web.services.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//webservices for  Project Status
@RestController
public class GetStatusApi {

	@Autowired
	private ProjectStatusProviderService projectStatusProviderService;

	@RequestMapping(value = "/api/admin/getStatus", method = RequestMethod.POST)
	public ResponseEntity<List<ProjectStatus>> listAllState() {
		List<ProjectStatus> projectStatuss = projectStatusProviderService
				.getProjectStatus();
		if (projectStatuss.isEmpty()) {
			return new ResponseEntity<List<ProjectStatus>>(
					HttpStatus.NO_CONTENT);// You many decide to return
											// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ProjectStatus>>(projectStatuss,HttpStatus.OK);
	}

	@RequestMapping(value = "/api/admin/getStatus/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect",
					ResultCodes.INVALID_REQUEST);
		}
		ProjectStatus ret = projectStatusProviderService
				.getProjectStatusById(idi);
		return ret == null ? new Result("Status not found",
				ResultCodes.STATE_NOT_FOUND) : ret;
	}

	@RequestMapping(value = "/api/admin/deleteStatus/{id}", method = RequestMethod.DELETE)
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
			ret = projectStatusProviderService.deleteProjectStatus(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation",	ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("Project Status not found",	ResultCodes.STATE_NOT_FOUND);
	}

	@RequestMapping(value = "/api/admin/saveStatus/", method = RequestMethod.POST)
	public Result saveStatus(@RequestParam String name) {
		ProjectStatus ns = new ProjectStatus();
		Boolean b = true;
		if (projectStatusProviderService.isAlreadyExist(name) == false) {
			ns.setName(name);
			if (projectStatusProviderService.addProjectStatus(ns) == true) {
				b = true;
				return new Result("successfull added ", 0);
			}
		} else {
			b = false;
			return new Result("Already Exist", ResultCodes.ALREADY_EXITS);
		}
		return new Result("Unknown Error", ResultCodes.ERROR);
	}

	@RequestMapping(value = "/api/admin/updateStatus/", method = RequestMethod.POST)
	public Result updatestate(@RequestParam("name") String name, @RequestParam("id") int id) {
		try {
			ProjectStatus ns = projectStatusProviderService.getProjectStatusById(id);
			ns.setName(name);
			Boolean b = true;
			if (projectStatusProviderService.isAlreadyExist(name) == false) {
				ns.setName(name);
				if (projectStatusProviderService.updateProjectStatus(ns) == true) {
					b = true;
					return new Result("successfull added ", 0);
				}

			} else {
				b = false;
				return new Result("Already Exist", ResultCodes.ALREADY_EXITS);
			}
			return new Result("Unknown Error", ResultCodes.ERROR);
		} catch (NullPointerException ex) {
			return new Result("Id not present", -7);
		}
	}

}
