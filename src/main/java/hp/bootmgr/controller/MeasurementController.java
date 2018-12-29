package hp.bootmgr.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import hp.bootmgr.services.MeasurementUnitService;
import hp.bootmgr.validators.MeasurementUnitValidator;
import hp.bootmgr.vo.MeasurementUnit;

@Controller
@SuppressWarnings("rawtypes")
public class MeasurementController {
	
	@Autowired
	private MeasurementUnitValidator measurementUnitValidator;
	@Autowired
	private MeasurementUnitService measurementUnitService;
	
	@RequestMapping(value="admin/addMeasurementUnit", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> addState(HttpSession session, @Valid @ModelAttribute("measurementModel") MeasurementUnit measurementUnit, BindingResult result) {
		if(result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors().forEach(error -> {
				sb.append("* ");
				sb.append(error.getDefaultMessage());
				sb.append("</br>");
			});
			session.setAttribute("errorMsg", sb.toString());
		} else session.setAttribute("status", measurementUnitService.save(measurementUnit));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@InitBinder
	private void InitBinder(WebDataBinder binder){
		binder.setValidator(measurementUnitValidator);		
	}

	@RequestMapping("admin/deleteMeasurementUnit")
	public ResponseEntity deleteState( HttpSession session,@RequestParam("id") int id) {
		MeasurementUnit measurementUnit=measurementUnitService.getById(id);
		HttpStatus status=HttpStatus.OK; 
		try {
			measurementUnitService.delete(measurementUnit);
		} catch(DataIntegrityViolationException ex) {
			session.setAttribute("errorMsg2", "You can not delete this property because it is being referenced by other records. Please delete all relevant data before attempting to delte this record.");
		} catch (Exception ex) {
			ex.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity(status);
	}
	
	@RequestMapping("admin/updateMeasurementUnit")
	public ResponseEntity updateState(@RequestParam("name") String name, @RequestParam("mid") int id) {
		MeasurementUnit target = measurementUnitService.getById(id);
		target.setName(name);
		return new ResponseEntity(measurementUnitService.update(target) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
