package hp.bootmgr.web.services;

import hp.bootmgr.services.StateManagementService;
import hp.bootmgr.vo.State;
import hp.bootmgr.web.services.responses.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//webservices for State
@RestController
public class GetStateApi {

	private static Logger logger = Logger.getLogger(GetStateApi.class);

	@Autowired
	private StateManagementService stateManagementService;

	@RequestMapping(value = "/api/admin/getState", method = RequestMethod.POST)
	public ResponseEntity<List<State>> listAllState() {
		List<State> state = stateManagementService.getAll();
		if (state.isEmpty()) {
			return new ResponseEntity<List<State>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<State>>(state, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/admin/getState/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect",
					ResultCodes.INVALID_REQUEST);
		}
		State ret = stateManagementService.getById(idi);
		return ret == null ? new Result("State not found",
				ResultCodes.STATE_NOT_FOUND) : ret;
	}

	@RequestMapping(value = "/api/admin/deletestate/{id}", method = RequestMethod.DELETE)
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
			ret = stateManagementService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation",	ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("State not found", ResultCodes.STATE_NOT_FOUND);
	}

	@RequestMapping(value = "/api/admin/updateState/", method = RequestMethod.POST)
	public Result updatestate(@RequestParam("stateName") String stateName,
							  @RequestParam("stateID") int id) {
		try {
			State ns = stateManagementService.getById(id);
			ns.setStateName(stateName);
			Boolean b = true;
			if (stateManagementService.isAlreadyExist(stateName) == false) {
				ns.setStateName(stateName);
				if (stateManagementService.update(ns) == true) {
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

	@RequestMapping(value = "/api/admin/saveState/", method = RequestMethod.POST)
	public Result saveState(@RequestParam String stateName) {
		State ns = new State();
		Boolean b = true;
		if (stateManagementService.isAlreadyExist(stateName) == false) {
			ns.setStateName(stateName);
			if (stateManagementService.save(ns) == true) {
				b = true;
				return new Result("successfull added ", 0);
			}
		} else {
			b = false;
			return new Result("Already Exist", ResultCodes.ALREADY_EXITS);
		}
		return new Result("Unknown Error", ResultCodes.ERROR);
	}

}
