package hp.bootmgr.web.services;

import java.util.List;

import hp.bootmgr.services.InquiryService;
import hp.bootmgr.vo.Inquiry;

import hp.bootmgr.web.services.responses.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetInquiryApi {

	@Autowired
	private InquiryService inquiryService;
	
	@RequestMapping(value = "/api/admin/getInquiry", method = RequestMethod.POST)
	public ResponseEntity<List<Inquiry>> listAllState() {
		List<Inquiry> inquirys = inquiryService.getAll();
		if(inquirys.isEmpty()){
			return new ResponseEntity<List<Inquiry>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Inquiry>>(inquirys, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/admin/getInquiry/{id}", method = RequestMethod.POST)
	public Object getById(@PathVariable("id") String id) {
		Integer idi = null;
		try {
			idi = Integer.parseInt(id);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		Inquiry ret = inquiryService.getById(idi);
		return ret == null ? new Result("Inquiry not found", ResultCodes.STATE_NOT_FOUND) : ret;
	}
	@RequestMapping(value = "/api/admin/deleteInquiry/{id}", method = RequestMethod.DELETE)
	public Object deleteById(@PathVariable("id") String id) {
		Integer idi = null;
		boolean ret = false;
		try {
			idi = Integer.parseInt(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new Result("Request sent by client was incorrect", ResultCodes.INVALID_REQUEST);
		}
		try {
			ret = inquiryService.deleteById(idi);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Constraint violation", ResultCodes.CONSTRAINT_VIOLATION);
		}
		return ret ? ret : new Result("inquiry not found", ResultCodes.STATE_NOT_FOUND);
	}
	

	@RequestMapping(value = "/api/admin/saveInquiry/", method = RequestMethod.POST)
	public boolean saveInquiry(@RequestParam Inquiry inquiry) {     //pass the request parameter
		inquiryService.save(inquiry);
		return true;
	}
	
	
	
}
