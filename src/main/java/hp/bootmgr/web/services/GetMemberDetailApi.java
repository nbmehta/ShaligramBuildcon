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
public class GetMemberDetailApi {
	
	@Autowired
	private EmployeeManagementService employeeManagementService;
	
	@RequestMapping(value = "/api/admin/Memberdetails", method = RequestMethod.POST)
	public ResponseEntity <List<Employee>> getAllMembers() {
		List<Employee> memberDetails = employeeManagementService.getAllMembers();
		if(memberDetails.isEmpty()){
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		 	return new ResponseEntity<List<Employee>>(memberDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/admin/Employees", method = RequestMethod.POST)
	public ResponseEntity <List<Employee>> getAllEmployee() {
		List<Employee> memberDetails = employeeManagementService.getAll();
		if(memberDetails.isEmpty()){
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		 	return new ResponseEntity<List<Employee>>(memberDetails, HttpStatus.OK);
	}

	@RequestMapping(value="/api/admin/"	+ "/{id}",method=RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		Employee ret = employeeManagementService.getById(idi);
		return ret == null ? new Result("Inquiry not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}
	
	@RequestMapping(value="/api/admin/updateMemberdetail/",method=RequestMethod.POST)
	public HashMap<String, Boolean> updateState( @RequestParam("id") String id,@RequestParam("firstName")String firstName,@RequestParam("city") String city,@RequestParam("lastName") String lastName,@RequestParam("email") String email,@RequestParam("mobileNo") String number,@RequestParam("address1") String address ) {
		HashMap<String,Boolean> obj=new HashMap<String, Boolean>();
		Boolean b=true;
		try{
		Employee target = employeeManagementService.getById(Integer.parseInt(id));
		target.setId(Integer.parseInt(id));
		target.setFirstName(firstName);
		target.setLastName(lastName);
		target.setCity(city);
		target.setMobileNo(number);
		target.setEmail(email);
		target.setAddress1(address);
		employeeManagementService.update(target);
		if(employeeManagementService.update(target)== true){
			b=true;
		}
		else {
			b=false;
			}
		}
		catch(NullPointerException ex)
		{
				System.out.println("id not found");
		}
		obj.put("status", b);
		return obj; 
			}
	@RequestMapping(value="/api/admin/saveEmployee/",method=RequestMethod.POST)
	public boolean saveEmployee() {
		return true;
	}
	
	
}