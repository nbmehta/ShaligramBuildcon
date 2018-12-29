package hp.bootmgr.controller;

import hp.bootmgr.services.ProjectPropertyBlockService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.validators.ProjectPropertyBlocksValidator;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectPropertyBlock;

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

@Controller
@SuppressWarnings("rawtypes")
public class ProjectPropertyBlockController {
	
	@Autowired
	private static Logger logger = Logger.getLogger(ProjectPropertyBlock.class);
	
	@Autowired
	private ProjectPropertyBlockService projectPropertyBlockService;
	
	@Autowired
	private ProjectService projectService;
	
	
	@RequestMapping(value="admin/addProjectPropertyBlock", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> addProjectPropertyBlock(HttpSession session, @Valid @ModelAttribute("propertyBlock") ProjectPropertyBlock projectPropertyBlock, BindingResult result) {	
		Project project = projectService.getById(projectPropertyBlock.getProject().getId());
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else {
			project.getBlocks().add(projectPropertyBlock);
			session.setAttribute("status", projectService.update(project));
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@RequestMapping("admin/deleteProjectPropertyBlock")
	public ResponseEntity deleteState(HttpSession session,@RequestParam("id") int id) {
		
		ProjectPropertyBlock projectPropertyBlock=projectPropertyBlockService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			projectPropertyBlockService.delete(projectPropertyBlock);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" +  projectPropertyBlock.getBlockId() + "] <= [" + projectPropertyBlock + "]");
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping(value="admin/updateProjectPropertyBlock", method=RequestMethod.POST)
	public ResponseEntity updateProjectPropertyBlock(@RequestParam("floorNo") int floors, @RequestParam("blockname") String newName, @RequestParam("blockID") int blkID) {
		logger.debug("updateProjectPropertyBlock() -> " + floors + ", " + newName + ", " + blkID);
		ProjectPropertyBlock target = projectPropertyBlockService.getById(blkID);
		target.setBlock(newName);
		target.setNoOfFloor(floors);
		return new ResponseEntity(projectPropertyBlockService.update(target) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping("admin/getBlockForProject/{id}")
	public @ResponseBody 
	List<ProjectPropertyBlock> getBlockForSelectedProject(@PathVariable int id) {
		return projectService.getById(id).getBlocks();
	}
	
	public class ProjectValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}
	
	@InitBinder
	public void FormBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Project.class, new ProjectValueBinder());
		binder.setValidator(new ProjectPropertyBlocksValidator());
	}
}
