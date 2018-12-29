package hp.bootmgr.controller;

import java.beans.PropertyEditorSupport;
import java.util.List;

import javax.servlet.http.HttpSession;

import hp.bootmgr.services.ProjectPropertyTypeService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.vo.Employee;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectPropertyBlock;
import hp.bootmgr.vo.ProjectPropertyType;
import hp.bootmgr.vo.PropertyType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SuppressWarnings("rawtypes")
public class ProjectPropertyTypeController {

	@Autowired
	private static Logger logger = Logger.getLogger(ProjectPropertyBlock.class);
	
	@Autowired
	private ProjectPropertyTypeService projectPropertyTypeService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private PropertyTypeProviderService propertyTypeProviderService;
	
	@RequestMapping(value="admin/addProjectPropertyType", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus > addProjectPropertyType(@ModelAttribute("projectPropType") ProjectPropertyType projectPropertyType, HttpSession session) {
		logger.debug("addProjectPropertyType()");
		session.setAttribute("status", projectPropertyTypeService.add(projectPropertyType));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@RequestMapping("admin/deleteProjectPropertyType")
	public ResponseEntity deleteState(@RequestParam("id") int id) {
		return new ResponseEntity(projectPropertyTypeService.deleteById(id) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value={"admin/getPropTypesForPrj/{id}", "user/getPropTypesForPrj/{id}"})
	public @ResponseBody
	List<PropertyType> getPropertyTypesForProject(@PathVariable int id) {
		return projectService.getById(id).getPropertyTypes();
	}
	
	@RequestMapping("admin/updateProjectPropertyType")
	public ResponseEntity updateProjectPropertyBlock(@RequestParam("project") Project project,@RequestParam("employee") Employee employee,@RequestParam("active") boolean active, @RequestParam("projContactPersonID") int id) {
		ProjectPropertyType target = projectPropertyTypeService.getById(id);
	
		return new ResponseEntity(projectPropertyTypeService.update(target) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="admin/getPropertyTypesForPrj/{id}", method=RequestMethod.GET)
	public @ResponseBody
	List<PropertyType> getPropTypesForPrj(@PathVariable int id) {
		return projectService.getById(id).getPropertyTypes();
	}
	
	public class ProjectValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}
	
	private class PropertyTypeValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(propertyTypeProviderService.getPropertyTypeById(Integer.parseInt(text)));
		}
	}
	
	@InitBinder
	public void FormBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Project.class, new ProjectValueBinder());
		binder.registerCustomEditor(PropertyType.class, new PropertyTypeValueBinder());
	}
}
