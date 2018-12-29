package hp.bootmgr.web.services;

import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.StateManagementService;
import hp.bootmgr.vo.Employee;
import hp.bootmgr.vo.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetchangePasswordApi {
	@Autowired
	private EmployeeManagementService employeeManagementService;
	@RequestMapping(value = "/api/admin/changePass/", method = RequestMethod.POST)
	public boolean getById(@RequestParam("id") String id ,@RequestParam("password") String password) {
		Integer idi = null;
			try {
			idi = Integer.parseInt(id);
			Employee ret = employeeManagementService.getById(idi);
			ret.setPassword(password);
			employeeManagementService.update(ret);
		} catch(NullPointerException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
}
