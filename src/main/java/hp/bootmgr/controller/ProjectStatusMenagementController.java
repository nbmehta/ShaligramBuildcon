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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hp.bootmgr.services.ProjectStatusProviderService;
import hp.bootmgr.validators.ProjectStatusValidator;
import hp.bootmgr.vo.ProjectStatus;

@Controller
@SuppressWarnings("rawtypes")
public class ProjectStatusMenagementController {
	
	
private static Logger logger = Logger.getLogger(PropertyManagementController.class);
	@Autowired
	private ProjectStatusValidator projectStatusValidator;
	
	@Autowired
	private ProjectStatusProviderService projectStatusProviderService;
	
	@RequestMapping(value="admin/updateProjectStatus", method=RequestMethod.POST)
	public ResponseEntity updateProjectStatus(HttpSession session, @RequestParam("projectStatus") String name, @RequestParam("projectStatus_id") int id) {
		logger.debug("Request -> Update -> ProjectStatus -> " + id);
		ProjectStatus projectStatus = projectStatusProviderService.getProjectStatusById(id);
		projectStatus.setName(name);
		return new ResponseEntity(projectStatusProviderService.updateProjectStatus(projectStatus) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping("admin/addProjectStatus")
	public ResponseEntity<HttpStatus> addPropertyType(HttpSession session, @Valid @ModelAttribute("projectStatusModel") ProjectStatus projectStatus, BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else session.setAttribute("status", projectStatusProviderService.addProjectStatus(projectStatus));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@RequestMapping("admin/deleteProjectStatus")
	public ResponseEntity deletePropertyType(HttpSession session, @RequestParam("id") int id) {
		logger.debug("Request -> Delete -> ProjectStatus -> " + id);
		HttpStatus status=HttpStatus.OK; 
		try {
			projectStatusProviderService.deleteProjectStatus(id);
		} catch(DataIntegrityViolationException ex) {
			
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(projectStatusValidator);
	}
}
