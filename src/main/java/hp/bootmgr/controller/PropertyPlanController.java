/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.controller;

import java.beans.PropertyEditorSupport;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hp.bootmgr.services.ProjectPropertyBlockService;
import hp.bootmgr.services.ProjectPropertyPlanService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.validators.ProjectPropertyPlanValidator;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectPropertyBlock;
import hp.bootmgr.vo.ProjectPropertyPlan;
import hp.bootmgr.vo.PropertyType;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SuppressWarnings("rawtypes")
public class PropertyPlanController {
	
	private static Logger logger = Logger.getLogger(PropertyPlanController.class);
	
	@Autowired
	private ProjectPropertyPlanService projectPropertyPlanService;
	
	@Autowired
	private PropertyTypeProviderService propertyTypeProviderService;
	
	@Autowired
	private ProjectPropertyBlockService projectPropertyBlockService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="admin/addPropertyPlan", method=RequestMethod.POST)
	private ResponseEntity<HttpStatus> addPropertyPlan(@Valid @ModelAttribute("propertyPlan") ProjectPropertyPlan propertyPlan,
													   BindingResult result, HttpSession session,
													   @RequestParam("planFile") MultipartFile file) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else {
			logger.debug("File Upload: " + file.getOriginalFilename() + " size = " + file.getSize());
			if(!file.isEmpty()) {
				try {
					// Files are stored in following path: UPLOADS/<project_name>_<block_name>_<floor_number>/filename.ext
					File uploadDirectory = new File(session.getServletContext().getRealPath("/") + "uploads" + File.separator + "property_plan" + File.separator + propertyPlan.getProject().getName().trim().replace(" ", "_") + "_" + propertyPlan.getBlock().getBlock().trim().replace(" ", "_") + "_" + propertyPlan.getFloorNumber());
					if(!uploadDirectory.exists())
						uploadDirectory.mkdirs();
					File fileToUpload = new File(uploadDirectory.getAbsolutePath() + File.separator + file.getOriginalFilename());
					logger.debug("File Upload: Saving File: " + fileToUpload.getAbsolutePath());
					BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToUpload));
					outputStream.write(file.getBytes());
					outputStream.close();

					propertyPlan.setPlanFilePath(file.getOriginalFilename());
					propertyPlan.setMimeType(file.getContentType());
					logger.debug("File Upload: Success!!!");
				} catch (IOException e) {
					logger.error("File Upload: Error while uploading file...!!!");
					e.printStackTrace();
				}
			} else logger.debug("File Upload: Skipping file upload because file was empty!!!");
			session.setAttribute("status", projectPropertyPlanService.save(propertyPlan));
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping("admin/deleteProjectPropertyPlan")
	public ResponseEntity deleteState(HttpSession session,@RequestParam("id") int id) {
		ProjectPropertyPlan projPropertyPlan=projectPropertyPlanService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			projectPropertyPlanService.delete(projPropertyPlan);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" +  projPropertyPlan.getId() + "] <= [" + projPropertyPlan + "]");
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping(value = "admin/updateProjectPropertyPlan")
	public ResponseEntity updateState(@ModelAttribute("updateProjectPropertyPlanModel") ProjectPropertyPlan name) {
		return new ResponseEntity(projectPropertyPlanService.update(name) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping("admin/getPlansForproject/{id}")
	private @ResponseBody
	List<ProjectPropertyPlan> getPlansForProject(@PathVariable int id) {
		return projectPropertyPlanService.getPlansForProject(id);
	}
	
	@RequestMapping("admin/getPlansForpropertyType/{id}")
	private @ResponseBody
	List<ProjectPropertyPlan> getPlansForPropertyType(@PathVariable int id) {
		return projectPropertyPlanService.getPlansForPropertyType(id);
	}
	
	@RequestMapping("admin/getPlansForblock/{id}")
	private @ResponseBody
	List<ProjectPropertyPlan> getPlansForBlocks(@PathVariable int id) {
		return projectPropertyPlanService.getPlansForBlock(id);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(int.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(ProjectPropertyBlock.class, new BlockPropertyEditorSupport());
		binder.registerCustomEditor(PropertyType.class, new PropertyTypeEditorSupport());
		binder.registerCustomEditor(Project.class, new ProjectPropertyEditorSupport());
		binder.setValidator(new ProjectPropertyPlanValidator());
	}
	
	private class BlockPropertyEditorSupport extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectPropertyBlockService.getById(Integer.parseInt(text)));
		}
	}
	
	private class PropertyTypeEditorSupport extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(propertyTypeProviderService.getPropertyTypeById(Integer.parseInt(text)));
		}
	}
	
	private class ProjectPropertyEditorSupport extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}
}
