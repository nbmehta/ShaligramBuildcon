package hp.bootmgr.web.services;

import java.util.HashMap;
import java.util.List;

import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.vo.Employee;

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
public class GetEmployeeApi {

	@Autowired
	private EmployeeManagementService employeeManagementService;

	@RequestMapping(value = "/api/admin/getEmployee", method = RequestMethod.POST )
	public ResponseEntity<List<Employee>> listAllState() {
		List<Employee> state = employeeManagementService.getAll();
		if(state.isEmpty()){
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Employee>>(state, HttpStatus.OK);
	}
	@RequestMapping(value = "/api/admin/getEmployee/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi=null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		Employee ret = employeeManagementService.getById(idi);
		return ret == null ? new Result("Employee not found", ResultCodes.STATE_NOT_FOUND) : ret;
	
	}
	@RequestMapping(value = "/api/admin/deleteEmployee/{id}", method = RequestMethod.POST)
	public Object deleteByid(@PathVariable("id") String id) {
		Integer idi = null;
		boolean ret = false;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		try {
			ret = employeeManagementService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation", ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("Employee not found", ResultCodes.STATE_NOT_FOUND);
	}
	@RequestMapping(value = "/api/admin/updateEmployee/", method = RequestMethod.POST)
	public ResponseEntity<Result> updateBookingDetail(@RequestParam HashMap<String, String> parms) {
		String id = parms.get("id");
		Result msg = null;
		try {
			Employee detail = employeeManagementService.getById(Integer.parseInt(id));
			detail.setEmail(parms.get("city"));
			employeeManagementService.update(detail);
			msg = new Result("success", ResultCodes.UPDATE_BOOKING_DETAIL);
		} catch(Exception ex) {
			msg = new Result("error", ResultCodes.UPDATE_BOOKING_DETAIL);
		}
		return new ResponseEntity<Result>(msg, HttpStatus.OK);
	}
	@RequestMapping(value = "/api/admin/deleteEmployee/", method = RequestMethod.POST)
	public Boolean deleteEmployee(@RequestParam Employee employee) {
		return true;
	}
	@RequestMapping(value = "/api/admin/mergeChanges/", method = RequestMethod.POST)
	public Boolean mergeChanges() {
			return true;
	}
}
