/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.controller;

import hp.bootmgr.services.ProjectTypeService;
import hp.bootmgr.validators.ProjectTypeValidator;
import hp.bootmgr.vo.ProjectType;

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

@Controller
@SuppressWarnings("rawtypes")
public class ProjectTypeManagementController {
	
	private static Logger logger = Logger.getLogger(ProjectTypeManagementController.class);
	@Autowired
	ProjectTypeValidator projectTypeValidator;
	
	@Autowired
	private ProjectTypeService projectTypeService;

	@RequestMapping(value = "admin/addProjectType", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addProjectType(HttpSession session, @Valid @ModelAttribute("projectTypeModel") ProjectType projectType, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else session.setAttribute("status", projectTypeService.add(projectType));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@InitBinder
	private void InitBinder(WebDataBinder binder){
		binder.setValidator( projectTypeValidator);	
	}
	
	@RequestMapping(value = "admin/updateProjectType", method = RequestMethod.POST)
	public ResponseEntity updatePropertyType(HttpSession session, @RequestParam("projectType") String name, @RequestParam("projectType_id") int id) {
		logger.debug("Request -> Update -> ProjectType -> " + id);
		ProjectType projectType = projectTypeService.getById(id);
		projectType.setName(name);
		return new ResponseEntity(projectTypeService.update(projectType) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping("admin/deleteProjectType")
	public ResponseEntity deletePropertyType(HttpSession session,@RequestParam("id") int id) {
		logger.debug("Request -> Delete -> ProjectType -> " + id);
		ProjectType projectType=projectTypeService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			projectTypeService.delete(projectType);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" +  projectType.getProjectTypeID() + "] <= [" + projectType + "]");
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}

}
