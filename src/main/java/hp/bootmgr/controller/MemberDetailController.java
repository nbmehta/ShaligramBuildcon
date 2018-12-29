/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.EmployeeRoleService;
import hp.bootmgr.vo.Employee;

@Controller
@SuppressWarnings("rawtypes")
@SessionAttributes("updateMemberDetailModel")
public class MemberDetailController {
	@Autowired
	private static Logger logger = Logger.getLogger(MemberDetailController.class);
	
	@Autowired
	private EmployeeManagementService employeeManagementService;
	
	@Autowired
	private EmployeeRoleService employeeRoleService;
	
	@RequestMapping(value="admin/addMemberDetail")
	public ResponseEntity<HttpStatus> addMemberDetail(HttpSession session,@Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else {
			employee.setActive(true);
			employee.setRole(employeeRoleService.getRoleByName("ROLE_MEMBER"));
			employeeManagementService.save(employee);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "admin/updateMemberDetail", method = RequestMethod.POST)
	public ResponseEntity updateMemberDetail(@ModelAttribute("updateMemberDetailModel") Employee employee) {
		logger.debug("Request -> Update -> MemberDetail -> " + employee.getId());
		return new ResponseEntity(employeeManagementService.update(employee) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="admin/deleteMemberDetail", method=RequestMethod.POST)
	public ResponseEntity deleteEmployee(HttpSession session ,@RequestParam("id") int id) {
		Employee employee = employeeManagementService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			employeeManagementService.delete(employee);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" + employee + "]");
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
}
