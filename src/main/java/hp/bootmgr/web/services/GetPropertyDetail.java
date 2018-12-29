package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.PropertyDetailService;
import hp.bootmgr.vo.ProjectStatus;
import hp.bootmgr.vo.PropertyDetail;
import hp.bootmgr.web.services.responses.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetPropertyDetail {

	@Autowired
	private PropertyDetailService propertyDetailService;
	

	@RequestMapping(value = "/api/admin/propertydetail", method = RequestMethod.POST)
	public ResponseEntity<List<PropertyDetail>> propertydetail() {
		List<PropertyDetail> propertydetail = propertyDetailService.getAll();

		if (propertydetail.isEmpty()) {
			return new ResponseEntity<List<PropertyDetail>>(HttpStatus.NO_CONTENT);// You many decide to return
			// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<PropertyDetail>>(propertydetail,	HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/api/admin/propertydetail/{id}",method=RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
	Integer idi = null;
	try {
		idi = Integer.parseInt(id);
	} catch (Exception ex) {
		ex.printStackTrace();
		return new Result("Request sent by client was incorrect",ResultCodes.INVALID_REQUEST);
	}
	PropertyDetail ret = propertyDetailService.getById(idi);
	return ret == null ? new Result("Property detail not found",	ResultCodes.STATE_NOT_FOUND) : ret;
	}
	
	@RequestMapping(value = "/api/admin/deletepropertydetail/{id}", method = RequestMethod.DELETE)
	public Object deleteById(@PathVariable("id") String id) {
	Integer idi = null;
	boolean ret = false;
	try {
		idi = Integer.parseInt(id);
	} catch (Exception ex) {
		ex.printStackTrace();
		return new Result("Request sent by client was incorrect",	ResultCodes.INVALID_REQUEST);
	}
	try {
		ret = propertyDetailService.deleteById(idi);
	} catch (Exception e) {
		e.printStackTrace();
		return new Result("Constraint violation",	ResultCodes.CONSTRAINT_VIOLATION);
	}
	return ret ? ret : new Result("Property detail  not found",	ResultCodes.STATE_NOT_FOUND);
	}


	
	
	
	

	

	
}
