package hp.bootmgr.controller;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import hp.bootmgr.services.ProjectExtraParkingService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.validators.ProjectExtraParkingValidator;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectExtraParking;
import hp.bootmgr.vo.ProjectPropertyBlock;

@Controller
@SuppressWarnings("rawtypes")
public class ProjectExtraParkingController {

	@Autowired
	private static Logger logger = Logger.getLogger(ProjectPropertyBlock.class);
	
	@Autowired
	private ProjectExtraParkingService projectExtraParkingService;
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="admin/addProjectExtraParking", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> addProjectExtraParking(HttpSession session, @Valid @ModelAttribute("extraParkingModel") ProjectExtraParking extraparking, BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else session.setAttribute("status", projectExtraParkingService.save(extraparking));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@InitBinder
	private void InitBinder(WebDataBinder binder){
		binder.setValidator(new ProjectExtraParkingValidator());	
	}
	
	@RequestMapping("admin/deleteProjectExtraParking")
	public ResponseEntity deleteState(HttpSession session ,@RequestParam("id") int id) {
		ProjectExtraParking projectExtraParking=projectExtraParkingService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			projectExtraParkingService.delete(projectExtraParking);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" +  projectExtraParking.getId() + "] <= [" + projectExtraParking + "]");
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping("admin/updateProjectExtraParking")
	public ResponseEntity updateState(@RequestParam("edtProject") Project project, @RequestParam("pNo") int pNo, @RequestParam("note") String note, @RequestParam("parkingID") int id) {
		ProjectExtraParking target = projectExtraParkingService.getById(id);
		logger.debug("updateProjectExtraParking() -> " + project + ", " + pNo + ", " + note + ", " + id);
		target.setParkingNo(pNo);
		target.setNote(note);
		target.setProject(project);
		return new ResponseEntity(projectExtraParkingService.update(target) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private class ProjectExtraParkingValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectExtraParkingService.getById(Integer.parseInt(text)));
		}
	}

	private class ProjectValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}
	
	@InitBinder
	public void FormBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(Project.class, new ProjectValueBinder());
		binder.registerCustomEditor(ProjectExtraParking.class, new ProjectExtraParkingValueBinder());
	}
}
