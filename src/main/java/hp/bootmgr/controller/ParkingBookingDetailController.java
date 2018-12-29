package hp.bootmgr.controller;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import hp.bootmgr.services.EmployeeManagementService;
import hp.bootmgr.services.ParkingBookingDetailService;
import hp.bootmgr.services.ProjectExtraParkingService;
import hp.bootmgr.services.ProjectService;
import hp.bootmgr.validators.BookParkingValidator;
import hp.bootmgr.vo.Employee;
import hp.bootmgr.vo.MemberDetail;
import hp.bootmgr.vo.ParkingBookingDetail;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.ProjectExtraParking;

@Controller
@SuppressWarnings("rawtypes")
public class ParkingBookingDetailController {
	
	@Autowired
	private ParkingBookingDetailService parkingBookingDetailService;
	
	@Autowired
	private ProjectExtraParkingService projectExtraParkingService;
	
	@Autowired
	private EmployeeManagementService employeeManagementService;

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value={"admin/addParkingBookingDetail", "user/addParkingBookingDetail"}, method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> addParkingBookingDetail(HttpSession session,@Valid @ModelAttribute("parkingModel") ParkingBookingDetail p,BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else session.setAttribute("status", parkingBookingDetailService.save(p));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@RequestMapping(value={"admin/deleteParkingBookingDetail", "user/deleteParkingBookingDetail"})
	public ResponseEntity deleteState(@RequestParam("id") int id) {
		return new ResponseEntity(parkingBookingDetailService.deleteById(id) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = {"admin/updateParkingBookingDetail", "user/updateParkingBookingDetail"})
	public ResponseEntity updateState(@ModelAttribute("updateParkingBookingDetailModel") ParkingBookingDetail parkingBookingDetail) {
		return new ResponseEntity(parkingBookingDetailService.update(parkingBookingDetail) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value={"admin/getExtraParkingForPrj/{id}", "user/getExtraParkingForPrj/{id}"})
	private @ResponseBody
	List<ProjectExtraParking> getextraParkingForProject(@PathVariable int id) {
		return parkingBookingDetailService.getextraParkingForProject(id);
	}
	
	private class EmployeeManagementValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(employeeManagementService.getById(Integer.parseInt(text)));
		}
	}  

	private class ProjectValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectService.getById(Integer.parseInt(text)));
		}
	}
	
	private class ProjectExtraParkingValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(projectExtraParkingService.getById(Integer.parseInt(text)));
		}
	}

	private class MemberDetailValueBinder extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			setValue(employeeManagementService.getById(Integer.parseInt(text)).getDetails());
		}
	}

	@InitBinder
	public void FormBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(Project.class, new ProjectValueBinder());
		binder.registerCustomEditor(ProjectExtraParking.class, new ProjectExtraParkingValueBinder());
		binder.registerCustomEditor(MemberDetail.class, new MemberDetailValueBinder());
		binder.registerCustomEditor(Employee.class,
				new EmployeeManagementValueBinder());
		binder.setValidator(new BookParkingValidator());
	}
}
