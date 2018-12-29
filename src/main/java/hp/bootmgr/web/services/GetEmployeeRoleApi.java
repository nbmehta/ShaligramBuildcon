package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.EmployeeRoleService;
import hp.bootmgr.vo.EmployeeRole;

import hp.bootmgr.web.services.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetEmployeeRoleApi {
	
	@Autowired
	private EmployeeRoleService employeeRoleService;

	@RequestMapping(value="/api/admin/getEmployeerole", method=RequestMethod.POST)
	  public List<EmployeeRole> getEmployeeRoleList() {
		  List<EmployeeRole> getRole=employeeRoleService.getAll();
		  return getRole;		  
	  }
	  
	  @RequestMapping(value="/api/admin/getEmployeerole/{id}", method=RequestMethod.POST)
	  public Object getById(@PathVariable("id") String id){
		   Integer idi = null;
			try {
				idi = Integer.parseInt(id);
			} catch(Exception ex) {
				ex.printStackTrace();
				return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
			}
			EmployeeRole ret = employeeRoleService.getById(idi);
			return ret == null ? new Result("Role Not Found", ResultCodes.ROLE_NOT_FOUND) : ret;
	  }
	  
	  
	  
	  @RequestMapping(value="/api/admin/deleteEmployeeRole/{id}", method=RequestMethod.DELETE)
	  public Object deleteById(@PathVariable("id") String id){
		  Integer idi = null;
			boolean ret = false;
			try {
				idi = Integer.parseInt(id);
			} catch (Exception ex) {
				ex.printStackTrace();
				return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
			}
			try {
				ret = employeeRoleService.deleteById(idi);
			} catch (Exception e) {
				e.printStackTrace();
				return new Result("Constraint violation", ResultCodes.CONSTRAINT_VIOLATION);
			}
			return ret ? ret : new Result("Role not found", ResultCodes.ROLE_NOT_FOUND);
		} 

	@RequestMapping(value = "/api/admin/deleteRole/", method = RequestMethod.DELETE)
	public Boolean deleteRole() {

		return true;
	}

	@RequestMapping(value = "/api/admin/updateRole/", method = RequestMethod.POST)
	public Boolean updateRole(@RequestParam int id,@RequestParam String role) {
		try{
							
		}catch(Exception ex)
		{
			
		}
		return true;
	}
	@RequestMapping(value = "/api/admin/saveRole/", method = RequestMethod.POST)
	public Boolean saveRole() {
		return true;
	}
	@RequestMapping(value = "/api/admin/isAlreadyExistRole/", method = RequestMethod.POST)
	public Boolean isAlreadyExist(@RequestParam String role) {
	try{
		employeeRoleService.isAlreadyExist(role);
		return true;
	}catch(Exception ex)
	{
	ex.printStackTrace();	
	return false;
	}
	}
	@RequestMapping(value = "/api/admin/getRolebyName/", method = RequestMethod.POST)
	public Boolean getRolebyName() {
		return true;
	} 
  }

