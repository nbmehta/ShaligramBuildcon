package hp.bootmgr.controller;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hp.bootmgr.services.InquiryService;
import hp.bootmgr.services.ProjectInquiryService;
import hp.bootmgr.services.ProjectPropertyTypeService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.vo.Inquiry;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectInquiry;
import hp.bootmgr.vo.ProjectPropertyType;

@Controller
@SuppressWarnings("rawtypes")
public class ProjectInquiryController {
	
	@Autowired
	private ProjectInquiryService projectInquiryService;
	
	@Autowired
	private ProjectPropertyTypeService projectPropertyTypeService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@RequestMapping("admin/deleteProjectInquiry")
	public ResponseEntity deleteState(@RequestParam("id") int id) {
		return new ResponseEntity(projectInquiryService.deleteById(id) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping("admin/updateProjectInquiry")
	public ResponseEntity updateState(@RequestParam("name") String name, @RequestParam("proInquiryID") int id) {
		ProjectInquiry target = projectInquiryService.getById(id);
		return new ResponseEntity(projectInquiryService.update(target) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	private class InquiryValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(inquiryService.getById(Integer.parseInt(text)));
		}
	}

	private class ProjectValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}
	
	private class ProjectPropertyTypeValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectPropertyTypeService.getById(Integer.parseInt(text)));
		}
	}

	@InitBinder
	public void FormBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(Project.class, new ProjectValueBinder());
		binder.registerCustomEditor(Inquiry.class, new InquiryValueBinder());
		binder.registerCustomEditor(ProjectPropertyType.class, new ProjectPropertyTypeValueBinder());
	}
}
