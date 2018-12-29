/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.controller;

import java.beans.PropertyEditorSupport;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hp.bootmgr.services.MeasurementUnitService;
import hp.bootmgr.services.ProjectPropertyBlockService;
import hp.bootmgr.services.ProjectPropertyPlanService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.services.PropertyDetailService;
import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.validators.PropertyTypeValidator;
import hp.bootmgr.vo.MeasurementUnit;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectPropertyBlock;
import hp.bootmgr.vo.ProjectPropertyPlan;
import hp.bootmgr.vo.PropertyDetail;
import hp.bootmgr.vo.PropertyType;

@Controller
@SuppressWarnings("rawtypes")
public class PropertyManagementController {
	
	private static Logger logger = Logger.getLogger(PropertyManagementController.class);
	
	@Autowired
	private PropertyTypeProviderService propertyTypeProviderService;
	
	@Autowired
	private PropertyDetailService propertyDetailService;
	
	@Autowired
	private ProjectPropertyBlockService projectPropertyBlockService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MeasurementUnitService measurementUnitService;
	
	@Autowired
	private ProjectPropertyPlanService projectPropertyPlanService;
	
	@Autowired
	private PropertyTypeValidator propertyTypeValidator;
	
	@RequestMapping(value="admin/updatePropertyType", method=RequestMethod.POST)
	public ResponseEntity updatePropertyType(HttpSession session, @RequestParam("edit_prop_type") String name, @RequestParam("edit_prop_type_id") int id) {
		logger.debug("Request -> Update -> PropertyType -> " + id);
		PropertyType propertyType = propertyTypeProviderService.getPropertyTypeById(id);
		propertyType.setName(name);
		return new ResponseEntity(propertyTypeProviderService.updatePropertyType(propertyType) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "admin/updatePropertyDetail")
	public ResponseEntity updateState(@ModelAttribute("updatePropertyDetailModel") PropertyDetail propertyDetail) {
		return new ResponseEntity(propertyDetailService.update(propertyDetail) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping("admin/addPropertyType")
	public ResponseEntity<HttpStatus> addPropertyType(HttpSession session, @Valid @ModelAttribute("propType") PropertyType propertyType, BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			}); 
			session.setAttribute("errorMsg", sb.toString());
		} else session.setAttribute("status", propertyTypeProviderService.addPropertyType(propertyType));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping("admin/deletePropertyType")
	public ResponseEntity deletePropertyType(HttpSession session, @RequestParam("id") int id) {
		logger.debug("Request -> Delete -> PropertyType -> " + id);
		PropertyType propertyType = propertyTypeProviderService.getPropertyTypeById(id);
		HttpStatus status = HttpStatus.OK;
		
		// Hibernate is weird sometimes :'(
		propertyType.getProjects().forEach(project -> {
			project.getPropertyTypes().remove(propertyType);
		});
		try {
			propertyTypeProviderService.deletePropertyType(propertyType);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" +  propertyType.getPropertyTypeID() + "] <= [" + propertyType + "]");
			session.setAttribute("errorMsg", "You can not delete this data because it is being referenced by other records. Please delete all relevant data before deleting this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping(value="admin/addPropertyDetail")
	public ResponseEntity<HttpStatus> addPropertyDetail(@ModelAttribute("propertyDetail") PropertyDetail propertyDetail, @RequestParam("propNumStart") Integer propNumStart, @RequestParam("propNumEnd") Integer propNumEnd, HttpSession session) {
		HttpStatus status = HttpStatus.OK;
		if(!(propNumEnd == null || propNumStart == null) && propNumEnd > propNumStart) {
			propertyDetailService.saveOrderedProperties(propertyDetail, propNumStart, propNumEnd);
		} else if(propNumEnd == null && propNumStart != null) {
			// End number is not specified that means this request is for single entry
			propertyDetail.setPropertyNumber(propNumStart);
			propertyDetailService.save(propertyDetail);
		} else status = HttpStatus.BAD_REQUEST;
		//session.setAttribute("status", propertyDetailService.save(propertyDetail));
		return new ResponseEntity<HttpStatus>(status);
	}
	
	@RequestMapping(value="admin/deletePropertyDetail", method=RequestMethod.POST)
	public ResponseEntity deletePropertyDetail(HttpSession session,@RequestParam("id") int id) {
		logger.debug("Request -> Delete -> memberdetail -> " + id);
		PropertyDetail propertyDetail=propertyDetailService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			propertyDetailService.delete(propertyDetail);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" +  propertyDetail.getId() + "] <= [" + propertyDetail + "]");
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping(value={"admin/getPropertiesForBlock/{blockId}", "user/getPropertiesForBlock/{blockId}"})
	public @ResponseBody
	List<PropertyDetail> getPropertiesForBlock(@PathVariable int blockId) {
		return propertyDetailService.getPropertiesByBlock(blockId);
	}
	
	@RequestMapping(value={"admin/getPropertiesForBlockOnlyNonBooked/{blockId}", "user/getPropertiesForBlockOnlyNonBooked/{blockId}"})
	public @ResponseBody
	List<PropertyDetail> getPropertiesForBlockOnlyNonBooked(@PathVariable int blockId) {
		return propertyDetailService.getPropertiesByBlock(blockId, true);
	}
	
	@InitBinder
	public void formDataBinder(WebDataBinder binder) {
		// Binder for PropertyTypeID -> Object
		binder.registerCustomEditor(PropertyType.class, new PropertyTypeEditorSupport());
		binder.registerCustomEditor(ProjectPropertyBlock.class, new BlockPropertyEditorSupport());
		binder.registerCustomEditor(Project.class, new ProjectPropertyEditorSupport());
		binder.registerCustomEditor(MeasurementUnit.class, new MeasurementUnitEditorSupport());
		binder.registerCustomEditor(ProjectPropertyPlan.class, new ProjectPropertyPlanEditorSupport());
	}
	
	@InitBinder("propType")
	public void setBinderFor(WebDataBinder binder) {
		binder.setValidator(propertyTypeValidator);
	}
	
	private class PropertyTypeEditorSupport extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(propertyTypeProviderService.getPropertyTypeById(Integer.parseInt(text)));
		}
	}
	
	private class BlockPropertyEditorSupport extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectPropertyBlockService.getById(Integer.parseInt(text)));
		}
	}
	
	private class ProjectPropertyEditorSupport extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			logger.debug("ProjectPropertyEditorSupport -> setAsText -> PARAM[" + text + "] RET[" + projectService.getById(Integer.parseInt(text)) + "]");
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}

	
	private class MeasurementUnitEditorSupport extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(measurementUnitService.getById(Integer.parseInt(text)));
		}
	}
	
	private class ProjectPropertyPlanEditorSupport extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectPropertyPlanService.getById(Integer.parseInt(text)));
		}
	}
}
