package hp.bootmgr.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hp.bootmgr.services.EmployeeRoleService;
import hp.bootmgr.validators.EmployeeRoleValidator;
import hp.bootmgr.vo.EmployeeRole;

@Controller
@SuppressWarnings("rawtypes")
public class EmployeeRoleController {

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@Autowired
	private EmployeeRoleValidator employeeRoleValidator; 
	
	private static Logger logger = Logger.getLogger(EmployeeRoleController.class);

	
	@RequestMapping(value="admin/addEmployeeRole", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> addEmployeeRole(@Valid @ModelAttribute("employeeRoleModel") EmployeeRole emprole, BindingResult result, HttpSession session) {
		// "ROLE_ADMIN" must be inserted externally in database.
		// To avoid confusion, prefix all roles being added by user with "ROLE_"
		// and make spring security happy.
		emprole.setName("ROLE_" + emprole.getName());
		
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else session.setAttribute("status", employeeRoleService.save(emprole));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator( employeeRoleValidator);
	}
	
	@RequestMapping("admin/deleteEmployeeRole")
	public ResponseEntity deleteState( HttpSession session,@RequestParam("id") int id) {
		EmployeeRole employeeRole=employeeRoleService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			employeeRoleService.delete(employeeRole);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" +  employeeRole.getId() + "] <= [" + employeeRole + "]");
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping("admin/updateEmployeeRole")
	public ResponseEntity updateState(@RequestParam("name") String name, @RequestParam("empRoleID") int id) {
		EmployeeRole target = employeeRoleService.getById(id);
		target.setName(name);
		return new ResponseEntity(employeeRoleService.update(target) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
