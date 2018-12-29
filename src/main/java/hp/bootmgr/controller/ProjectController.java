package hp.bootmgr.controller;

import hp.bootmgr.binders.ProjectFormBinder;
import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.services.ProjectStatusProviderService;
import hp.bootmgr.services.ProjectTypeService;
import hp.bootmgr.services.PropertyTypeProviderService;
import hp.bootmgr.services.StateManagementService;
import hp.bootmgr.validators.ManageProjectValidator;
import hp.bootmgr.vo.*;

import java.beans.PropertyEditorSupport;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SuppressWarnings("rawtypes")
@SessionAttributes("projectUpdateModel")
public class ProjectController {
	
	@Autowired
	private static Logger logger = Logger.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private StateManagementService stateManagementService;
	
	@Autowired
	private ProjectTypeService projectTypeService;
	
	@Autowired
	private ProjectStatusProviderService projectStatusProviderService;
	
	@Autowired 
	private EmployeeManagementService employeeManagementService;
	
	@Autowired
	private PropertyTypeProviderService propertyTypeProviderService;
	
	@RequestMapping(value="admin/addProject", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> addProject(@Valid @ModelAttribute("newUser") ProjectFormBinder formBinder,
												 BindingResult result,
                                                 @RequestParam("files") MultipartFile[] files,
												 HttpSession session) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else {
			Project project = formBinder.getProject();
			List<Employee> employees = formBinder.getEmployees();
			project.setContactPerson(employees);
			// Here comes the Lambdas :D
			employees.forEach((emp) -> {emp.getProjectAssigned().add(project);});
			List<PropertyType> propTypes = formBinder.getPropTypes();
			project.setPropertyTypes(propTypes);

            File uploadDir = new File(session.getServletContext().getRealPath("/") + "uploads" + File.separator + "project_files" + File.separator + formBinder.getProject().getName().replace(" ", "_"));
            if(!uploadDir.exists())
                uploadDir.mkdirs();
            for(MultipartFile file : files) {
                logger.debug("File: " + file.getOriginalFilename() + " Type: " + file.getContentType() + " size: " + (file.getSize() / 1024) + " KB");
                File fileToUpload = new File(uploadDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                try {
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToUpload));
                    outputStream.write(file.getBytes());
                    outputStream.close();
                    ProjectFile projectFile = new ProjectFile();
                    projectFile.setFilename(file.getOriginalFilename());
                    projectFile.setMimeType(file.getContentType());
                    project.getProjectFiles().add(projectFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
			projectService.save(project);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@RequestMapping(value="admin/deleteProject", method=RequestMethod.POST)
	public ResponseEntity deleteProject(HttpSession session,@RequestParam("id") int id) {
		logger.debug("Request -> Delete -> Project -> " + id);
		Project project=projectService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			projectService.delete(project);
		} catch(DataIntegrityViolationException ex) {
			logger.error("Foreign Key constraint failed for [" +  project.getId() + "] <= [" + project + "]");
			session.setAttribute("errorMsg", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@InitBinder("newUser")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(new ManageProjectValidator() );
	}
	
	@RequestMapping(value="admin/updateProject", method=RequestMethod.POST)
	public ResponseEntity updateProject(@ModelAttribute("projectUpdateModel") ProjectFormBinder binder,
										@RequestParam("files") MultipartFile[] files,
										HttpSession session) {
		Project project = binder.getProject();
		logger.debug("Request -> Update -> Project -> " + project.getId());
		List<Employee> employees = binder.getEmployees();
		List<PropertyType> propTypes = binder.getPropTypes();
		project.setContactPerson(employees);
		project.setPropertyTypes(propTypes);

        File uploadDir = new File(session.getServletContext().getRealPath("/") + "uploads" + File.separator + "project_files" + File.separator + binder.getProject().getName().replace(" ", "_"));
        if(!uploadDir.exists())
            uploadDir.mkdirs();
        for(MultipartFile file : files) {
            logger.debug("File: " + file.getOriginalFilename() + " Type: " + file.getContentType() + " size: " + (file.getSize() / 1024) + " KB");
            File fileToUpload = new File(uploadDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            try {
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToUpload));
                outputStream.write(file.getBytes());
                outputStream.close();
                ProjectFile projectFile = new ProjectFile();
                projectFile.setFilename(file.getOriginalFilename());
                projectFile.setMimeType(file.getContentType());
                project.getProjectFiles().add(projectFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

		//employees.forEach((emp) -> {
		//	List<Project> prj = emp.getProjectAssigned();
		//	if(!prj.contains(emp))
		//		prj.add(project);
		//});
		projectService.update(project);
		return new ResponseEntity(projectService.update(project) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	private class StateValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(stateManagementService.getById(Integer.parseInt(text)));
		}
	}
		
	private class ProjectTypeValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectTypeService.getById(Integer.parseInt(text)));
		}
	}
	
	private class ProjectStatusProviderValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectStatusProviderService.getProjectStatusById(Integer.parseInt(text)));
		}
	}
	
	@InitBinder
	public void formBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(State.class, new StateValueBinder());
		binder.registerCustomEditor(ProjectType.class, new ProjectTypeValueBinder());
		binder.registerCustomEditor(ProjectStatus.class, new ProjectStatusProviderValueBinder());
		binder.registerCustomEditor(List.class, "employees", new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				logger.debug("formBinder() -> employees -> " + text);
				ArrayList<Employee> employees = new ArrayList<Employee>();
				String str[] = text.split(",");
				for(String s : str) {
					Employee emp = employeeManagementService.getById(Integer.parseInt(s));
					if(emp != null) employees.add(emp);
				}
				setValue(employees);
			}
		});
		binder.registerCustomEditor(List.class, "propTypes", new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				logger.debug("formBinder() -> propTypes -> " + text);
				ArrayList<PropertyType> propTypes = new ArrayList<PropertyType>();
				String str[] = text.split(",");
				for(String s : str) {
					PropertyType type = propertyTypeProviderService.getPropertyTypeById(Integer.parseInt(s));
					if(type != null) propTypes.add(type);
				}
				setValue(propTypes);
			}
		});
	}	
}
